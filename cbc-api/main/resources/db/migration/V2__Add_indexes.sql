-- Add an index on external_id to optimize searches
CREATE INDEX idx_external_id ON items(external_id);
