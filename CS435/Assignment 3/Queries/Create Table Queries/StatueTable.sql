CREATE TABLE Statue(
	ID INT NOT NULL AUTO_INCREMENT,
    ArtObjectID INT NOT NULL REFERENCES ArtObject(ID),
    Material VARCHAR(30) NOT NULL,
	Height INT NOT NULL,
    Weight INT NOT NULL,
    Style VARCHAR(30) NOT NULL,
    PRIMARY KEY(ID)
);