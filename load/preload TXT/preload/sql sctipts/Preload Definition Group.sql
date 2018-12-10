
---------------------------------
-- Ficha Interfaces Batch Group -
---------------------------------

-- Delete the old version. (THIS IS A CASADE DETELE!!!!!!)
DELETE FROM "LD_PRELOAD_DEFINITION_GROUP" WHERE PRELOAD_DEFINITION_GROUP_ID = 1;

-- Create the new preload definition group
INSERT INTO "LD_PRELOAD_DEFINITION_GROUP" (PRELOAD_DEFINITION_GROUP_ID, NAME, DESCRIPTION) VALUES ('1', 'Ficha Interfaces Batch', 'Ficha Interfaces Batch');

commit;
