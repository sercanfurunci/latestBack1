-- Önce status kolonunu geçici bir kolona kopyalayalım
ALTER TABLE sc_seniorproject.offers ADD COLUMN temp_status VARCHAR(20);
UPDATE sc_seniorproject.offers SET temp_status = status;

-- Status kolonunu düşürelim ve yeniden oluşturalım
ALTER TABLE sc_seniorproject.offers DROP COLUMN status;
ALTER TABLE sc_seniorproject.offers ADD COLUMN status VARCHAR(20);

-- Geçici kolondan verileri geri kopyalayalım
UPDATE sc_seniorproject.offers SET status = temp_status;

-- Geçici kolonu silelim
ALTER TABLE sc_seniorproject.offers DROP COLUMN temp_status;

-- NOT NULL constraint ekleyelim
ALTER TABLE sc_seniorproject.offers ALTER COLUMN status SET NOT NULL;

-- Check constraint ekleyelim
ALTER TABLE sc_seniorproject.offers 
    ADD CONSTRAINT offers_status_check 
    CHECK (status IN ('PENDING', 'ACCEPTED', 'REJECTED', 'CANCELLED')); 