-- Table: public.lab_examination

-- DROP TABLE IF EXISTS public.lab_examination;

CREATE TABLE IF NOT EXISTS public.lab_examination
(
    id bigint NOT NULL DEFAULT nextval('lab_examination_id_seq'::regclass),
    approval_date_time timestamp(6) without time zone,
    cancellation_reason character varying(255) COLLATE pg_catalog."default",
    doctor_notices character varying(255) COLLATE pg_catalog."default",
    execution_date_time timestamp(6) without time zone,
    ordered_date timestamp(6) without time zone NOT NULL,
    result character varying(255) COLLATE pg_catalog."default",
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    supervisor_notices character varying(255) COLLATE pg_catalog."default",
    examination_dictionary character varying(255) COLLATE pg_catalog."default" NOT NULL,
    lab_assist bigint,
    visit bigint NOT NULL,
    lab_super bigint,
    CONSTRAINT lab_examination_pkey PRIMARY KEY (id),
    CONSTRAINT fk2vfq8gvpnnucxbft3n3w2xik8 FOREIGN KEY (lab_assist)
        REFERENCES public.lab_assistant (lab_staff) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk7d79jkp5u2ju0kpu9koa7d4ue FOREIGN KEY (examination_dictionary)
        REFERENCES public.examination_dictionary (code) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fklmlq369ff7kbtdrb3edyf4jhj FOREIGN KEY (visit)
        REFERENCES public.visit (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fksv4yw47ox93mw322dhr8v56am FOREIGN KEY (lab_super)
        REFERENCES public.lab_supervisor (lab_staff) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT lab_examination_status_check CHECK (status::text = ANY (ARRAY['ORDERED'::character varying, 'COMPLETED'::character varying, 'CANCELLED'::character varying, 'APPROVED'::character varying, 'REJECTED'::character varying]::text[]))
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.lab_examination
    OWNER to postgres;