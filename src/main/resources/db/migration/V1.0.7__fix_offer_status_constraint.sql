-- Önce mevcut tüm constraint'leri kaldır
ALTER TABLE offers DROP CONSTRAINT IF EXISTS offers_status_check;

-- Status kolonunu VARCHAR olarak değiştir
ALTER TABLE offers ALTER COLUMN status TYPE VARCHAR(20);

-- Yeni constraint ekle
ALTER TABLE offers ADD CONSTRAINT offers_status_check 
    CHECK (status IN ('PENDING', 'ACCEPTED', 'REJECTED', 'CANCELLED')); 