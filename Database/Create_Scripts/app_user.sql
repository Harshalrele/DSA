-- Table: public.app_user

-- DROP TABLE IF EXISTS public.app_user;

CREATE TABLE IF NOT EXISTS public.app_user
(
    id bigint NOT NULL DEFAULT nextval('app_user_id_seq'::regclass),
    approved boolean NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    role character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT app_user_pkey PRIMARY KEY (id),
    CONSTRAINT app_user_role_check CHECK (role::text = ANY (ARRAY['USER'::character varying, 'ADMIN'::character varying, 'DOCTOR'::character varying, 'RECEPTIONIST'::character varying, 'LAB_ASSISTANT'::character varying, 'LAB_SUPER'::character varying, 'PATIENT'::character varying]::text[]))
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.app_user
    OWNER to postgres;