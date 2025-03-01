ALTER TABLE offers DROP CONSTRAINT IF EXISTS offers_status_check;
ALTER TABLE offers ADD CONSTRAINT offers_status_check 
    CHECK (status IN ('PENDING', 'ACCEPTED', 'REJECTED', 'CANCELLED')); 