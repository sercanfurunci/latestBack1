DROP TABLE IF EXISTS notifications CASCADE;

CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    title VARCHAR(255) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    link VARCHAR(255),
    type VARCHAR(50) NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT notifications_type_check CHECK (
        type IN (
            'NEW_OFFER', 'OFFER_ACCEPTED', 'OFFER_REJECTED', 'OFFER_CANCELLED', 'OFFER_STATUS_CHANGE',
            'ORDER_CREATED', 'ORDER_SHIPPED', 'ORDER_DELIVERED', 'ORDER_CANCELLED', 'ORDER_STATUS_CHANGE',
            'PRODUCT_CREATED', 'PRODUCT_UPDATED', 'PRODUCT_DELETED', 'PRODUCT_APPROVED', 'PRODUCT_REJECTED',
            'PRODUCT_STATUS_CHANGE', 'PRODUCT_STOCK_LOW', 'PRODUCT_OUT_OF_STOCK',
            'USER_REGISTERED', 'USER_VERIFIED', 'USER_BLOCKED', 'SELLER_VERIFIED', 'SELLER_REJECTED',
            'COMMENT_RECEIVED', 'COMMENT_REPLIED', 'COMMENT_REPORTED',
            'NEW_MESSAGE', 'MESSAGE_READ',
            'SYSTEM_MESSAGE', 'MAINTENANCE', 'ERROR'
        )
    )
);

CREATE INDEX idx_notifications_user_id ON notifications(user_id);
CREATE INDEX idx_notifications_created_at ON notifications(created_at DESC);
CREATE INDEX idx_notifications_is_read ON notifications(is_read);
CREATE INDEX idx_notifications_type ON notifications(type); 