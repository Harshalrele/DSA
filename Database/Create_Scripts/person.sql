-- Table: public.person

-- DROP TABLE IF EXISTS public.person;

CREATE TABLE IF NOT EXISTS public.person
(
    nationalidnumber character varying(255) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    sex character varying(255) COLLATE pg_catalog."default" NOT NULL,
    app_user bigint NOT NULL,
    CONSTRAINT person_pkey PRIMARY KEY (nationalidnumber),
    CONSTRAINT uk_8mr53bm4ukh5yu19amqwqku3x UNIQUE (app_user),
    CONSTRAINT fkmebx5o59h6gqkdhetwnn332ct FOREIGN KEY (app_user)
        REFERENCES public.app_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT person_sex_check CHECK (sex::text = ANY (ARRAY['MALE'::character varying, 'FEMALE'::character varying, 'OTHER'::character varying]::text[]))
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.person
    OWNER to postgres;