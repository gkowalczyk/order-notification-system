CREATE TABLE ORDER_EVENT_AUDIT
    (EVENT_ID UUID PRIMARY KEY,
     shipment_number TEXT NOT NULL,
     recipient_email TEXT NOT NULL,
     recipient_country VARCHAR(2) NOT NULL,
     sender_country VARCHAR(2) NOT NULL,
     status_code INT NOT NULL CHECK (status_code BETWEEN 0 AND 100),
     received_at TIMESTAMP NOT NULL
);