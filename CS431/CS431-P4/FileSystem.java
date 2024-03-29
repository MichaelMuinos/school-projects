import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FileSystem {
	
	private static final Pattern PUT_COMMAND = Pattern.compile("put .+ [0-9]+");
	private static final Pattern DEL_COMMAND = Pattern.compile("del .+");
	private static final String BITMAP_COMMAND = "bitmap";
	private static final String INODE_COMMAND = "inodes";
	private static final Scanner sc = new Scanner(System.in);
	
	private static int[] fat = new int[64];
	private static Map<String,INode> iNodes = new HashMap<String,INode>();
	private static long bitmap = 0;
	
	public static void main(String[] args) {
		showCommandLineInterface();
	}
	
	private static void showCommandLineInterface() {
		while(true) {
			System.out.print("> ");
			String command = sc.nextLine();
			// split command based on space
			String[] splitCommand = command.split(" ");
			if(command.matches(PUT_COMMAND.pattern())) {
				String fileName = splitCommand[1];
				int size = Integer.parseInt(splitCommand[2]);
				if(isAllowedOperation(fileName, size))
					setBits(fileName, size);
				else
					System.out.println("Error: Can't do operation!");
			} else if(command.matches(DEL_COMMAND.pattern())) {
				String fileName = splitCommand[1];
				if(canRemove(fileName)) {
					clearBits(iNodes.get(fileName).getStartingBlock());
					iNodes.remove(fileName);
				} else
					System.out.println("Error: Can't do operation!");
			} else if(command.equals(BITMAP_COMMAND)) {
				printBitmap();
			} else if(command.equals(INODE_COMMAND)) {
				printINodes();
			} else {
				System.out.println("Bad command!");
			}
		}
	}
	
	private static boolean canRemove(String fileName) {
		return iNodes.containsKey(fileName) ? true : false;
	}
	
	private static boolean isAllowedOperation(String fileName, int size) {
		return !iNodes.containsKey(fileName) && hasEnoughSpace(size) ? true : false;
	}
	
	private static boolean hasEnoughSpace(int size) {
		int counter = 0;
		int blockCounter = 0;
		long curBit = 1;
		while(counter < fat.length) {
			if((curBit & bitmap) == 0) ++blockCounter;
			curBit <<= 1;
			++counter;
		}
		return blockCounter >= size ? true : false;
	}
	
	private static void setBits(String fileName, int size) {
		int counter = 0;
		int blockCounter = 0;
		int start = 0;
		boolean foundFirstBlock = false;
		long curBit = 1;
		while(counter < fat.length && blockCounter < size) {
			if((curBit & bitmap) == 0) {
				if(!foundFirstBlock) {
					INode iNode = new INode(fileName, counter, size);
					iNodes.put(fileName, iNode);
					foundFirstBlock = true;
					start = counter;
				}
				bitmap |= ((long) 1 << counter);
				++blockCounter;
				fat[start] = counter;
				start = counter;
			}
			curBit <<= 1;
			++counter;
		}
		fat[start] = -1;
	}
	
	private static void clearBits(int startBlock) {
		int val = startBlock;
		while(val != -1) {
			bitmap &= ~((long) 1 << val);
			val = fat[val];
		}
	}
	
	private static void printBitmap() {
		int block = 0;
		int counter = 0;
		long curBit = 1;
		System.out.print(" " + block + " ");
		while(counter < fat.length) {
			if(counter != 0 && counter % 8 == 0) {
				System.out.println();
				block += 8;
				if(counter == 8)
					System.out.print(" " + block + " ");
				else
					System.out.print(block + " ");
			}
			if((curBit & bitmap) == 0)
				System.out.print(0);
			else
				System.out.print(1);
			curBit <<= 1;
			++counter;
		}
		System.out.println();
	}
	
	private static void printINodes() {
		for(Map.Entry<String,INode> entry : iNodes.entrySet()) {
			System.out.print(entry.getKey() + ": ");
			int val = entry.getValue().getStartingBlock();
			while(val != -1) {
				System.out.print(val + " -> ");
				val = fat[val];
			}
			System.out.print("END");
			System.out.println();
		}
	}

}
