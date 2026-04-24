-- Table: public.examination_dictionary

-- DROP TABLE IF EXISTS public.examination_dictionary;

CREATE TABLE IF NOT EXISTS public.examination_dictionary
(
    code character varying(255) COLLATE pg_catalog."default" NOT NULL,
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    rights_level character varying(255) COLLATE pg_catalog."default" NOT NULL,
    type character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT examination_dictionary_pkey PRIMARY KEY (code),
    CONSTRAINT examination_dictionary_rights_level_check CHECK (rights_level::text = ANY (ARRAY['HIGH'::character varying, 'MEDIUM'::character varying, 'LOW'::character varying, 'NONE'::character varying]::text[])),
    CONSTRAINT examination_dictionary_type_check CHECK (type::text = ANY (ARRAY['PHYSICAL'::character varying, 'LABORATORY'::character varying]::text[]))
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.examination_dictionary
    OWNER to postgres;