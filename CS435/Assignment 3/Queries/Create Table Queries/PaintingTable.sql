CREATE TABLE Painting(
	ID INT NOT NULL AUTO_INCREMENT,
    ArtObjectID INT NOT NULL REFERENCES ArtObject(ID),
    Material VARCHAR(30) NOT NULL,
    Style VARCHAR(30) NOT NULL,
    PaintType VARCHAR(30) NOT NULL,
    PRIMARY KEY(ID)
);