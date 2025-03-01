-- Önce eski constraint'i kaldır
ALTER TABLE notifications DROP CONSTRAINT IF EXISTS notifications_type_check;

-- Yeni constraint'i ekle
ALTER TABLE notifications ADD CONSTRAINT notifications_type_check 
CHECK (type::text = ANY (ARRAY[
    'NEW_OFFER'::text,
    'OFFER_ACCEPTED'::text,
    'OFFER_REJECTED'::text,
    'OFFER_STATUS_CHANGE'::text,
    'ORDER_CREATED'::text,
    'ORDER_STATUS_CHANGE'::text,
    'PRODUCT_STATUS_CHANGE'::text,
    'SYSTEM_MESSAGE'::text,
    'NEW_MESSAGE'::text
])); 