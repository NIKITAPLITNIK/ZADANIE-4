CREATE TABLE IF NOT EXISTS contacts (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    firstName TEXT NOT NULL,
    lastName TEXT NOT NULL,
    phoneNumber1 TEXT,
    phoneNumber2 TEXT,
    phoneNumber3 TEXT
);
