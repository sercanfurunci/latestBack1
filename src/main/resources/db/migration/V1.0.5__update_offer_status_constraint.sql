-- Önce mevcut constraint'i kaldır
ALTER TABLE offers DROP CONSTRAINT IF EXISTS offers_status_check;

-- Yeni constraint'i ekle
ALTER TABLE offers ADD CONSTRAINT offers_status_check 
    CHECK (status::text IN ('PENDING', 'ACCEPTED', 'REJECTED', 'CANCELLED')); 