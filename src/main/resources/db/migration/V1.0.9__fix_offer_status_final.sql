-- Önce tüm ilgili constraint'leri kaldır
ALTER TABLE sc_seniorproject.offers DROP CONSTRAINT IF EXISTS offers_status_check;

-- Status değerlerini düzelt
UPDATE sc_seniorproject.offers 
SET status = CASE 
    WHEN status = 'pending' THEN 'PENDING'
    WHEN status = 'accepted' THEN 'ACCEPTED'
    WHEN status = 'rejected' THEN 'REJECTED'
    WHEN status = 'cancelled' THEN 'CANCELLED'
    ELSE 'PENDING'
END;

-- Status kolonunu yeniden tanımla
ALTER TABLE sc_seniorproject.offers ALTER COLUMN status TYPE VARCHAR(20);
ALTER TABLE sc_seniorproject.offers ALTER COLUMN status SET NOT NULL;

-- Yeni constraint ekle
ALTER TABLE sc_seniorproject.offers 
ADD CONSTRAINT offers_status_check 
CHECK (status IN ('PENDING', 'ACCEPTED', 'REJECTED', 'CANCELLED')); 