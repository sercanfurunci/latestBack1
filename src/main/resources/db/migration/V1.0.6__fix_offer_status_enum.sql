-- Önce mevcut constraint'i kaldır
ALTER TABLE offers DROP CONSTRAINT IF EXISTS offers_status_check;

-- Enum tipini oluştur (eğer yoksa)
DO $$ 
BEGIN 
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'offer_status') THEN
        CREATE TYPE offer_status AS ENUM ('PENDING', 'ACCEPTED', 'REJECTED', 'CANCELLED');
    END IF;
END $$;

-- Status kolonunu enum tipine dönüştür
ALTER TABLE offers 
    ALTER COLUMN status TYPE offer_status 
    USING status::offer_status;

-- Yeni constraint ekle
ALTER TABLE offers 
    ADD CONSTRAINT offers_status_check 
    CHECK (status::text = ANY (ARRAY['PENDING', 'ACCEPTED', 'REJECTED', 'CANCELLED'])); 