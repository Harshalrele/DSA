-- Table: public.refresh_token

-- DROP TABLE IF EXISTS public.refresh_token;

CREATE TABLE IF NOT EXISTS public.refresh_token
(
    id bigint NOT NULL DEFAULT nextval('refresh_token_id_seq'::regclass),
    revoked boolean NOT NULL,
    token character varying(10000) COLLATE pg_catalog."default" NOT NULL,
    app_user bigint NOT NULL,
    CONSTRAINT refresh_token_pkey PRIMARY KEY (id),
    CONSTRAINT uk_r4k4edos30bx9neoq81mdvwph UNIQUE (token),
    CONSTRAINT fksqhr2ooxsl17ilcyuo4gl1r7c FOREIGN KEY (app_user)
        REFERENCES public.app_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.refresh_token
    OWNER to postgres;