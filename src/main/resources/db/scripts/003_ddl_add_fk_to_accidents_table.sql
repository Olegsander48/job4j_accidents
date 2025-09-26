ALTER TABLE accidents
ADD COLUMN accident_type_id int REFERENCES accident_types(id);