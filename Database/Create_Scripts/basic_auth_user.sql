-- Table: public.basic_auth_user

-- DROP TABLE IF EXISTS public.basic_auth_user;

CREATE TABLE IF NOT EXISTS public.basic_auth_user
(
    id bigint NOT NULL DEFAULT nextval('basic_auth_user_id_seq'::regclass),
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    app_user bigint NOT NULL,
    CONSTRAINT basic_auth_user_pkey PRIMARY KEY (id),
    CONSTRAINT uk_3ls5k2wfq9b21l924a6pypan8 UNIQUE (app_user),
    CONSTRAINT fk5wwq7nhbmvnfw0m2lp5ky444o FOREIGN KEY (app_user)
        REFERENCES public.app_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.basic_auth_user
    OWNER to postgres;