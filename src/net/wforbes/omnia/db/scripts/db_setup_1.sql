--
-- File generated with SQLiteStudio v3.4.4 on Fri Dec 8 02:04:38 2023
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: flora
DROP TABLE IF EXISTS flora;

CREATE TABLE IF NOT EXISTS flora (
    id         INTEGER PRIMARY KEY AUTOINCREMENT
                       NOT NULL,
    name       TEXT    UNIQUE
                       NOT NULL,
    sprite_dir TEXT    NOT NULL
);

INSERT INTO flora (
  id,
  name,
  sprite_dir
)
VALUES (
  1,
  'shrub',
  '/overworld/terrain/flora/shrubs'
);

INSERT INTO flora (
  id,
  name,
  sprite_dir
)
VALUES (
  2,
  'tree',
  '/overworld/terrain/flora/trees'
);


-- Table: flora_type
DROP TABLE IF EXISTS flora_type;

CREATE TABLE IF NOT EXISTS flora_type (
    id              INTEGER PRIMARY KEY AUTOINCREMENT
                            UNIQUE
                            NOT NULL,
    flora_id        INTEGER REFERENCES flora (id)
                            NOT NULL,
    genera          TEXT    UNIQUE
                            NOT NULL,
    size            INTEGER,
    sprite_filename TEXT    NOT NULL
);

INSERT INTO flora_type (
   id,
   flora_id,
   genera,
   size,
   sprite_filename
)
VALUES (
   1,
   1,
   'blueberry',
   0,
   'Bush_blue_flowers3.png'
);

INSERT INTO flora_type (
   id,
   flora_id,
   genera,
   size,
   sprite_filename
)
VALUES (
   2,
   2,
   'oak',
   0,
   'fixed_Tree1.png'
);


-- Table: flora_type_harvest_loot
DROP TABLE IF EXISTS flora_type_harvest_loot;

CREATE TABLE IF NOT EXISTS flora_type_harvest_loot (
    id            INTEGER PRIMARY KEY
                          UNIQUE
                          NOT NULL,
    flora_type_id INTEGER REFERENCES flora_type (id) ON UPDATE CASCADE
                          NOT NULL,
    item_id       INTEGER REFERENCES item (id) ON UPDATE CASCADE
                          NOT NULL,
    drop_rate     REAL    NOT NULL
);

INSERT INTO flora_type_harvest_loot (
    id,
    flora_type_id,
    item_id,
    drop_rate
)
VALUES (
    1,
    1,
    1,
    0.5
);

INSERT INTO flora_type_harvest_loot (
    id,
    flora_type_id,
    item_id,
    drop_rate
)
VALUES (
    2,
    1,
    2,
    0.8
);

INSERT INTO flora_type_harvest_loot (
    id,
    flora_type_id,
    item_id,
    drop_rate
)
VALUES (
    3,
    1,
    3,
    0.01
);

INSERT INTO flora_type_harvest_loot (
    id,
    flora_type_id,
    item_id,
    drop_rate
)
VALUES (
    4,
    1,
    4,
    0.8
);

INSERT INTO flora_type_harvest_loot (
    id,
    flora_type_id,
    item_id,
    drop_rate
)
VALUES (
    5,
    2,
    4,
    1.0
);


-- Table: item
DROP TABLE IF EXISTS item;

CREATE TABLE IF NOT EXISTS item (
    id            INTEGER PRIMARY KEY AUTOINCREMENT
                          NOT NULL,
    name          TEXT    UNIQUE
                          NOT NULL,
    description   TEXT,
    icon_location TEXT    NOT NULL
);

INSERT INTO item (
    id,
    name,
    description,
    icon_location
 )
 VALUES (
    1,
    'Blueberry',
    'Delicious berries, high in antioxidants.',
    '/overworld/inventory/icons/ingredients/1.png'
 );

INSERT INTO item (
    id,
    name,
    description,
    icon_location
)
VALUES (
    2,
    'Leaf',
    'Just a leaf, nothing special.',
    '/overworld/inventory/icons/plants/leaf.png'
);

INSERT INTO item (
    id,
    name,
    description,
    icon_location
)
VALUES (
    3,
    'Botanical Energy',
    'The life energy of a botanical being. Useful for magic spells.',
    '/overworld/inventory/icons/plants/botanical_energy.png'
);

INSERT INTO item (
    id,
    name,
    description,
    icon_location
)
VALUES (
    4,
    'Sticks',
    'Just some sticks, nothing special.',
    '/overworld/inventory/icons/plants/sticks.png'
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
