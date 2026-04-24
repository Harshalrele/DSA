-- Table: public.physical_examination

-- DROP TABLE IF EXISTS public.physical_examination;

CREATE TABLE IF NOT EXISTS public.physical_examination
(
    id bigint NOT NULL DEFAULT nextval('physical_examination_id_seq'::regclass),
    examination_date_time timestamp(6) without time zone,
    result character varying(255) COLLATE pg_catalog."default",
    examination_dictionary character varying(255) COLLATE pg_catalog."default",
    visit bigint,
    CONSTRAINT physical_examination_pkey PRIMARY KEY (id),
    CONSTRAINT fk31y2lxc3wnmbdhw2ql1u1m75d FOREIGN KEY (visit)
        REFERENCES public.visit (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk5yq5ltrcy1oh4g2q4dma9sxk6 FOREIGN KEY (examination_dictionary)
        REFERENCES public.examination_dictionary (code) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.physical_examination
    OWNER to postgres;