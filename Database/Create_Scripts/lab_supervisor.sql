-- Table: public.lab_supervisor

-- DROP TABLE IF EXISTS public.lab_supervisor;

CREATE TABLE IF NOT EXISTS public.lab_supervisor
(
    id bigint NOT NULL DEFAULT nextval('lab_supervisor_id_seq'::regclass),
    rights_level character varying(255) COLLATE pg_catalog."default" NOT NULL,
    lab_staff bigint NOT NULL,
    CONSTRAINT lab_supervisor_pkey PRIMARY KEY (id),
    CONSTRAINT uk_kc30unn7bnrbbyrh1tsub196t UNIQUE (lab_staff),
    CONSTRAINT fkcwrcd0kc7n2cwk5ffgcf1dfqf FOREIGN KEY (lab_staff)
        REFERENCES public.lab_staff (lab_emp_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT lab_supervisor_rights_level_check CHECK (rights_level::text = ANY (ARRAY['HIGH'::character varying, 'MEDIUM'::character varying, 'LOW'::character varying, 'NONE'::character varying]::text[]))
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.lab_supervisor
    OWNER to postgres;