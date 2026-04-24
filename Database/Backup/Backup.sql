--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2024-07-07 15:46:32

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 4964 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 54304)
-- Name: app_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.app_user (
    id bigint NOT NULL,
    approved boolean NOT NULL,
    email character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    CONSTRAINT app_user_role_check CHECK (((role)::text = ANY ((ARRAY['USER'::character varying, 'ADMIN'::character varying, 'DOCTOR'::character varying, 'RECEPTIONIST'::character varying, 'LAB_ASSISTANT'::character varying, 'LAB_SUPER'::character varying, 'PATIENT'::character varying])::text[])))
);


ALTER TABLE public.app_user OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 54303)
-- Name: app_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.app_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.app_user_id_seq OWNER TO postgres;

--
-- TOC entry 4965 (class 0 OID 0)
-- Dependencies: 215
-- Name: app_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.app_user_id_seq OWNED BY public.app_user.id;


--
-- TOC entry 218 (class 1259 OID 54314)
-- Name: basic_auth_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.basic_auth_user (
    id bigint NOT NULL,
    password character varying(255) NOT NULL,
    app_user bigint NOT NULL
);


ALTER TABLE public.basic_auth_user OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 54313)
-- Name: basic_auth_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.basic_auth_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.basic_auth_user_id_seq OWNER TO postgres;

--
-- TOC entry 4966 (class 0 OID 0)
-- Dependencies: 217
-- Name: basic_auth_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.basic_auth_user_id_seq OWNED BY public.basic_auth_user.id;


--
-- TOC entry 220 (class 1259 OID 54321)
-- Name: clinic_staff; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clinic_staff (
    clinic_emp_id bigint NOT NULL,
    person character varying(255) NOT NULL
);


ALTER TABLE public.clinic_staff OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 54320)
-- Name: clinic_staff_clinic_emp_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clinic_staff_clinic_emp_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.clinic_staff_clinic_emp_id_seq OWNER TO postgres;

--
-- TOC entry 4967 (class 0 OID 0)
-- Dependencies: 219
-- Name: clinic_staff_clinic_emp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clinic_staff_clinic_emp_id_seq OWNED BY public.clinic_staff.clinic_emp_id;


--
-- TOC entry 222 (class 1259 OID 54328)
-- Name: doctor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.doctor (
    id bigint NOT NULL,
    npwz_id character varying(255) NOT NULL,
    clinic_staff bigint NOT NULL
);


ALTER TABLE public.doctor OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 54327)
-- Name: doctor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.doctor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.doctor_id_seq OWNER TO postgres;

--
-- TOC entry 4968 (class 0 OID 0)
-- Dependencies: 221
-- Name: doctor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.doctor_id_seq OWNED BY public.doctor.id;


--
-- TOC entry 223 (class 1259 OID 54334)
-- Name: examination_dictionary; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.examination_dictionary (
    code character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    rights_level character varying(255) NOT NULL,
    type character varying(255) NOT NULL,
    CONSTRAINT examination_dictionary_rights_level_check CHECK (((rights_level)::text = ANY ((ARRAY['HIGH'::character varying, 'MEDIUM'::character varying, 'LOW'::character varying, 'NONE'::character varying])::text[]))),
    CONSTRAINT examination_dictionary_type_check CHECK (((type)::text = ANY ((ARRAY['PHYSICAL'::character varying, 'LABORATORY'::character varying])::text[])))
);


ALTER TABLE public.examination_dictionary OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 54369)
-- Name: lab_assistant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lab_assistant (
    id bigint NOT NULL,
    lab_staff bigint NOT NULL
);


ALTER TABLE public.lab_assistant OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 54368)
-- Name: lab_assistant_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lab_assistant_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.lab_assistant_id_seq OWNER TO postgres;

--
-- TOC entry 4969 (class 0 OID 0)
-- Dependencies: 230
-- Name: lab_assistant_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lab_assistant_id_seq OWNED BY public.lab_assistant.id;


--
-- TOC entry 225 (class 1259 OID 54344)
-- Name: lab_examination; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lab_examination (
    id bigint NOT NULL,
    approval_date_time timestamp(6) without time zone,
    cancellation_reason character varying(255),
    doctor_notices character varying(255),
    execution_date_time timestamp(6) without time zone,
    ordered_date timestamp(6) without time zone NOT NULL,
    result character varying(255),
    status character varying(255) NOT NULL,
    supervisor_notices character varying(255),
    examination_dictionary character varying(255) NOT NULL,
    lab_assist bigint,
    visit bigint NOT NULL,
    lab_super bigint,
    CONSTRAINT lab_examination_status_check CHECK (((status)::text = ANY ((ARRAY['ORDERED'::character varying, 'COMPLETED'::character varying, 'CANCELLED'::character varying, 'APPROVED'::character varying, 'REJECTED'::character varying])::text[])))
);


ALTER TABLE public.lab_examination OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 54343)
-- Name: lab_examination_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lab_examination_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.lab_examination_id_seq OWNER TO postgres;

--
-- TOC entry 4970 (class 0 OID 0)
-- Dependencies: 224
-- Name: lab_examination_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lab_examination_id_seq OWNED BY public.lab_examination.id;


--
-- TOC entry 227 (class 1259 OID 54354)
-- Name: lab_staff; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lab_staff (
    lab_emp_id bigint NOT NULL,
    person character varying(255) NOT NULL
);


ALTER TABLE public.lab_staff OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 54353)
-- Name: lab_staff_lab_emp_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lab_staff_lab_emp_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.lab_staff_lab_emp_id_seq OWNER TO postgres;

--
-- TOC entry 4971 (class 0 OID 0)
-- Dependencies: 226
-- Name: lab_staff_lab_emp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lab_staff_lab_emp_id_seq OWNED BY public.lab_staff.lab_emp_id;


--
-- TOC entry 229 (class 1259 OID 54361)
-- Name: lab_supervisor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lab_supervisor (
    id bigint NOT NULL,
    rights_level character varying(255) NOT NULL,
    lab_staff bigint NOT NULL,
    CONSTRAINT lab_supervisor_rights_level_check CHECK (((rights_level)::text = ANY ((ARRAY['HIGH'::character varying, 'MEDIUM'::character varying, 'LOW'::character varying, 'NONE'::character varying])::text[])))
);


ALTER TABLE public.lab_supervisor OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 54360)
-- Name: lab_supervisor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lab_supervisor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.lab_supervisor_id_seq OWNER TO postgres;

--
-- TOC entry 4972 (class 0 OID 0)
-- Dependencies: 228
-- Name: lab_supervisor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lab_supervisor_id_seq OWNED BY public.lab_supervisor.id;


--
-- TOC entry 232 (class 1259 OID 54375)
-- Name: patient; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.patient (
    insurance_id character varying(255) NOT NULL,
    person character varying(255) NOT NULL
);


ALTER TABLE public.patient OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 54382)
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    nationalidnumber character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    sex character varying(255) NOT NULL,
    app_user bigint NOT NULL,
    CONSTRAINT person_sex_check CHECK (((sex)::text = ANY ((ARRAY['MALE'::character varying, 'FEMALE'::character varying, 'OTHER'::character varying])::text[])))
);


ALTER TABLE public.person OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 54391)
-- Name: physical_examination; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.physical_examination (
    id bigint NOT NULL,
    examination_date_time timestamp(6) without time zone,
    result character varying(255),
    examination_dictionary character varying(255),
    visit bigint
);


ALTER TABLE public.physical_examination OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 54390)
-- Name: physical_examination_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.physical_examination_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.physical_examination_id_seq OWNER TO postgres;

--
-- TOC entry 4973 (class 0 OID 0)
-- Dependencies: 234
-- Name: physical_examination_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.physical_examination_id_seq OWNED BY public.physical_examination.id;


--
-- TOC entry 237 (class 1259 OID 54400)
-- Name: receptionist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.receptionist (
    id bigint NOT NULL,
    clinic_staff bigint NOT NULL
);


ALTER TABLE public.receptionist OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 54399)
-- Name: receptionist_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.receptionist_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.receptionist_id_seq OWNER TO postgres;

--
-- TOC entry 4974 (class 0 OID 0)
-- Dependencies: 236
-- Name: receptionist_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.receptionist_id_seq OWNED BY public.receptionist.id;


--
-- TOC entry 239 (class 1259 OID 54407)
-- Name: refresh_token; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.refresh_token (
    id bigint NOT NULL,
    revoked boolean NOT NULL,
    token character varying(10000) NOT NULL,
    app_user bigint NOT NULL
);


ALTER TABLE public.refresh_token OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 54406)
-- Name: refresh_token_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.refresh_token_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.refresh_token_id_seq OWNER TO postgres;

--
-- TOC entry 4975 (class 0 OID 0)
-- Dependencies: 238
-- Name: refresh_token_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.refresh_token_id_seq OWNED BY public.refresh_token.id;


--
-- TOC entry 241 (class 1259 OID 54416)
-- Name: visit; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visit (
    id bigint NOT NULL,
    description character varying(255),
    diagnostics character varying(255),
    scheduled_date timestamp(6) without time zone NOT NULL,
    status character varying(255) NOT NULL,
    patient character varying(255) NOT NULL,
    receptionist bigint NOT NULL,
    selected_doctor bigint NOT NULL,
    CONSTRAINT visit_status_check CHECK (((status)::text = ANY ((ARRAY['REGISTERED'::character varying, 'IN_PROGRESS'::character varying, 'COMPLETED'::character varying, 'CANCELLED'::character varying])::text[])))
);


ALTER TABLE public.visit OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 54415)
-- Name: visit_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.visit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.visit_id_seq OWNER TO postgres;

--
-- TOC entry 4976 (class 0 OID 0)
-- Dependencies: 240
-- Name: visit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visit_id_seq OWNED BY public.visit.id;


--
-- TOC entry 4701 (class 2604 OID 54307)
-- Name: app_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.app_user ALTER COLUMN id SET DEFAULT nextval('public.app_user_id_seq'::regclass);


--
-- TOC entry 4702 (class 2604 OID 54317)
-- Name: basic_auth_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.basic_auth_user ALTER COLUMN id SET DEFAULT nextval('public.basic_auth_user_id_seq'::regclass);


--
-- TOC entry 4703 (class 2604 OID 54324)
-- Name: clinic_staff clinic_emp_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clinic_staff ALTER COLUMN clinic_emp_id SET DEFAULT nextval('public.clinic_staff_clinic_emp_id_seq'::regclass);


--
-- TOC entry 4704 (class 2604 OID 54331)
-- Name: doctor id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor ALTER COLUMN id SET DEFAULT nextval('public.doctor_id_seq'::regclass);


--
-- TOC entry 4708 (class 2604 OID 54372)
-- Name: lab_assistant id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_assistant ALTER COLUMN id SET DEFAULT nextval('public.lab_assistant_id_seq'::regclass);


--
-- TOC entry 4705 (class 2604 OID 54347)
-- Name: lab_examination id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_examination ALTER COLUMN id SET DEFAULT nextval('public.lab_examination_id_seq'::regclass);


--
-- TOC entry 4706 (class 2604 OID 54357)
-- Name: lab_staff lab_emp_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_staff ALTER COLUMN lab_emp_id SET DEFAULT nextval('public.lab_staff_lab_emp_id_seq'::regclass);


--
-- TOC entry 4707 (class 2604 OID 54364)
-- Name: lab_supervisor id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_supervisor ALTER COLUMN id SET DEFAULT nextval('public.lab_supervisor_id_seq'::regclass);


--
-- TOC entry 4709 (class 2604 OID 54394)
-- Name: physical_examination id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.physical_examination ALTER COLUMN id SET DEFAULT nextval('public.physical_examination_id_seq'::regclass);


--
-- TOC entry 4710 (class 2604 OID 54403)
-- Name: receptionist id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receptionist ALTER COLUMN id SET DEFAULT nextval('public.receptionist_id_seq'::regclass);


--
-- TOC entry 4711 (class 2604 OID 54410)
-- Name: refresh_token id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refresh_token ALTER COLUMN id SET DEFAULT nextval('public.refresh_token_id_seq'::regclass);


--
-- TOC entry 4712 (class 2604 OID 54419)
-- Name: visit id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit ALTER COLUMN id SET DEFAULT nextval('public.visit_id_seq'::regclass);


--
-- TOC entry 4933 (class 0 OID 54304)
-- Dependencies: 216
-- Data for Name: app_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.app_user (id, approved, email, role) FROM stdin;
1	t	patient1@email.com	PATIENT
2	t	patient2@email.com	PATIENT
3	t	patient3@email.com	PATIENT
4	t	patient4@email.com	PATIENT
5	t	patient5@email.com	PATIENT
6	t	doctor1@email.com	DOCTOR
7	t	doctor2@email.com	DOCTOR
8	t	doctor3@email.com	DOCTOR
9	t	barcic2948@gmail.com	RECEPTIONIST
10	t	receptionist2@email.com	RECEPTIONIST
11	t	assistant1@email.com	LAB_ASSISTANT
12	t	assistant2@email.com	LAB_ASSISTANT
13	t	assistant3@email.com	LAB_ASSISTANT
14	t	supervisor1@email.com	LAB_SUPER
15	t	supervisor2@email.com	LAB_SUPER
\.


--
-- TOC entry 4935 (class 0 OID 54314)
-- Dependencies: 218
-- Data for Name: basic_auth_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.basic_auth_user (id, password, app_user) FROM stdin;
1	patient1	1
2	patient2	2
3	patient3	3
4	patient4	4
5	patient5	5
6	doctor1	6
7	doctor2	7
8	doctor3	8
9	receptionist1	9
10	receptionist2	10
11	assistant1	11
12	assistant2	12
13	assistant3	13
14	supervisor1	14
15	supervisor2	15
\.


--
-- TOC entry 4937 (class 0 OID 54321)
-- Dependencies: 220
-- Data for Name: clinic_staff; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clinic_staff (clinic_emp_id, person) FROM stdin;
1	8749321
2	5567810
3	1234567
4	9876543
5	3456789
\.


--
-- TOC entry 4939 (class 0 OID 54328)
-- Dependencies: 222
-- Data for Name: doctor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.doctor (id, npwz_id, clinic_staff) FROM stdin;
1	npwzId1	1
2	npwzId2	2
3	npwzId3	3
\.


--
-- TOC entry 4940 (class 0 OID 54334)
-- Dependencies: 223
-- Data for Name: examination_dictionary; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.examination_dictionary (code, description, rights_level, type) FROM stdin;
TEMP	Measure temperature	NONE	PHYSICAL
PRES	Measure pressure	NONE	PHYSICAL
WEIG	Record weight	NONE	PHYSICAL
CARDIO	Cardiovascular assessment	NONE	PHYSICAL
NEURO	Neurological examination	NONE	PHYSICAL
STETH	Use stethoscope	NONE	PHYSICAL
XRAY	Take xray	LOW	LABORATORY
BIOP	Order biopsy	MEDIUM	LABORATORY
EKG	Perform EKG	HIGH	LABORATORY
ULTRA	Perform ultrasound	LOW	LABORATORY
BLOOD	Draw blood	MEDIUM	LABORATORY
ECG	Conduct ECG	HIGH	LABORATORY
URINE	Collect urine sample	LOW	LABORATORY
MRI	Perform MRI scan	MEDIUM	LABORATORY
FECAL	Analyze fecal sample	HIGH	LABORATORY
\.


--
-- TOC entry 4948 (class 0 OID 54369)
-- Dependencies: 231
-- Data for Name: lab_assistant; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lab_assistant (id, lab_staff) FROM stdin;
1	1
2	2
3	3
\.


--
-- TOC entry 4942 (class 0 OID 54344)
-- Dependencies: 225
-- Data for Name: lab_examination; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lab_examination (id, approval_date_time, cancellation_reason, doctor_notices, execution_date_time, ordered_date, result, status, supervisor_notices, examination_dictionary, lab_assist, visit, lab_super) FROM stdin;
\.


--
-- TOC entry 4944 (class 0 OID 54354)
-- Dependencies: 227
-- Data for Name: lab_staff; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lab_staff (lab_emp_id, person) FROM stdin;
1	9871234
2	5678901
3	3450129
4	8765432
5	2109876
\.


--
-- TOC entry 4946 (class 0 OID 54361)
-- Dependencies: 229
-- Data for Name: lab_supervisor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lab_supervisor (id, rights_level, lab_staff) FROM stdin;
1	LOW	4
2	HIGH	5
\.


--
-- TOC entry 4949 (class 0 OID 54375)
-- Dependencies: 232
-- Data for Name: patient; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.patient (insurance_id, person) FROM stdin;
insurancep1	7890123
insurancep2	2345678
insurancep3	8901234
insurancep4	4567890
insurancep5	6543210
\.


--
-- TOC entry 4950 (class 0 OID 54382)
-- Dependencies: 233
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.person (nationalidnumber, first_name, last_name, sex, app_user) FROM stdin;
7890123	Olivia	Davis	FEMALE	1
2345678	James	Miller	MALE	2
8901234	Isabella	Wilson	FEMALE	3
4567890	William	Moore	MALE	4
6543210	Ava	Taylor	FEMALE	5
8749321	John	Smith	MALE	6
5567810	Emma	Johnson	FEMALE	7
1234567	Michael	Williams	MALE	8
9876543	Sophia	Jones	FEMALE	9
3456789	David	Brown	MALE	10
9871234	Benjamin	Anderson	MALE	11
5678901	Mia	Thomas	FEMALE	12
3450129	Alexander	Jackson	MALE	13
8765432	Charlotte	White	FEMALE	14
2109876	Ethan	Harris	MALE	15
\.


--
-- TOC entry 4952 (class 0 OID 54391)
-- Dependencies: 235
-- Data for Name: physical_examination; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.physical_examination (id, examination_date_time, result, examination_dictionary, visit) FROM stdin;
\.


--
-- TOC entry 4954 (class 0 OID 54400)
-- Dependencies: 237
-- Data for Name: receptionist; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.receptionist (id, clinic_staff) FROM stdin;
1	4
2	5
\.


--
-- TOC entry 4956 (class 0 OID 54407)
-- Dependencies: 239
-- Data for Name: refresh_token; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.refresh_token (id, revoked, token, app_user) FROM stdin;
\.


--
-- TOC entry 4958 (class 0 OID 54416)
-- Dependencies: 241
-- Data for Name: visit; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.visit (id, description, diagnostics, scheduled_date, status, patient, receptionist, selected_doctor) FROM stdin;
\.


--
-- TOC entry 4977 (class 0 OID 0)
-- Dependencies: 215
-- Name: app_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.app_user_id_seq', 15, true);


--
-- TOC entry 4978 (class 0 OID 0)
-- Dependencies: 217
-- Name: basic_auth_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.basic_auth_user_id_seq', 15, true);


--
-- TOC entry 4979 (class 0 OID 0)
-- Dependencies: 219
-- Name: clinic_staff_clinic_emp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.clinic_staff_clinic_emp_id_seq', 5, true);


--
-- TOC entry 4980 (class 0 OID 0)
-- Dependencies: 221
-- Name: doctor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.doctor_id_seq', 3, true);


--
-- TOC entry 4981 (class 0 OID 0)
-- Dependencies: 230
-- Name: lab_assistant_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lab_assistant_id_seq', 3, true);


--
-- TOC entry 4982 (class 0 OID 0)
-- Dependencies: 224
-- Name: lab_examination_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lab_examination_id_seq', 1, false);


--
-- TOC entry 4983 (class 0 OID 0)
-- Dependencies: 226
-- Name: lab_staff_lab_emp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lab_staff_lab_emp_id_seq', 5, true);


--
-- TOC entry 4984 (class 0 OID 0)
-- Dependencies: 228
-- Name: lab_supervisor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lab_supervisor_id_seq', 2, true);


--
-- TOC entry 4985 (class 0 OID 0)
-- Dependencies: 234
-- Name: physical_examination_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.physical_examination_id_seq', 1, false);


--
-- TOC entry 4986 (class 0 OID 0)
-- Dependencies: 236
-- Name: receptionist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.receptionist_id_seq', 2, true);


--
-- TOC entry 4987 (class 0 OID 0)
-- Dependencies: 238
-- Name: refresh_token_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.refresh_token_id_seq', 1, false);


--
-- TOC entry 4988 (class 0 OID 0)
-- Dependencies: 240
-- Name: visit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visit_id_seq', 1, false);


--
-- TOC entry 4721 (class 2606 OID 54312)
-- Name: app_user app_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT app_user_pkey PRIMARY KEY (id);


--
-- TOC entry 4723 (class 2606 OID 54319)
-- Name: basic_auth_user basic_auth_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.basic_auth_user
    ADD CONSTRAINT basic_auth_user_pkey PRIMARY KEY (id);


--
-- TOC entry 4727 (class 2606 OID 54326)
-- Name: clinic_staff clinic_staff_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clinic_staff
    ADD CONSTRAINT clinic_staff_pkey PRIMARY KEY (clinic_emp_id);


--
-- TOC entry 4731 (class 2606 OID 54333)
-- Name: doctor doctor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (id);


--
-- TOC entry 4735 (class 2606 OID 54342)
-- Name: examination_dictionary examination_dictionary_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.examination_dictionary
    ADD CONSTRAINT examination_dictionary_pkey PRIMARY KEY (code);


--
-- TOC entry 4747 (class 2606 OID 54374)
-- Name: lab_assistant lab_assistant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_assistant
    ADD CONSTRAINT lab_assistant_pkey PRIMARY KEY (id);


--
-- TOC entry 4737 (class 2606 OID 54352)
-- Name: lab_examination lab_examination_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_examination
    ADD CONSTRAINT lab_examination_pkey PRIMARY KEY (id);


--
-- TOC entry 4739 (class 2606 OID 54359)
-- Name: lab_staff lab_staff_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_staff
    ADD CONSTRAINT lab_staff_pkey PRIMARY KEY (lab_emp_id);


--
-- TOC entry 4743 (class 2606 OID 54367)
-- Name: lab_supervisor lab_supervisor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_supervisor
    ADD CONSTRAINT lab_supervisor_pkey PRIMARY KEY (id);


--
-- TOC entry 4751 (class 2606 OID 54381)
-- Name: patient patient_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT patient_pkey PRIMARY KEY (insurance_id);


--
-- TOC entry 4755 (class 2606 OID 54389)
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (nationalidnumber);


--
-- TOC entry 4759 (class 2606 OID 54398)
-- Name: physical_examination physical_examination_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.physical_examination
    ADD CONSTRAINT physical_examination_pkey PRIMARY KEY (id);


--
-- TOC entry 4761 (class 2606 OID 54405)
-- Name: receptionist receptionist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receptionist
    ADD CONSTRAINT receptionist_pkey PRIMARY KEY (id);


--
-- TOC entry 4765 (class 2606 OID 54414)
-- Name: refresh_token refresh_token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT refresh_token_pkey PRIMARY KEY (id);


--
-- TOC entry 4725 (class 2606 OID 54426)
-- Name: basic_auth_user uk_3ls5k2wfq9b21l924a6pypan8; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.basic_auth_user
    ADD CONSTRAINT uk_3ls5k2wfq9b21l924a6pypan8 UNIQUE (app_user);


--
-- TOC entry 4763 (class 2606 OID 54442)
-- Name: receptionist uk_55xw2iid3ghs9bujtxhpl3147; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receptionist
    ADD CONSTRAINT uk_55xw2iid3ghs9bujtxhpl3147 UNIQUE (clinic_staff);


--
-- TOC entry 4729 (class 2606 OID 54428)
-- Name: clinic_staff uk_6rtux1lced3tevdbknjkdmy43; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clinic_staff
    ADD CONSTRAINT uk_6rtux1lced3tevdbknjkdmy43 UNIQUE (person);


--
-- TOC entry 4757 (class 2606 OID 54440)
-- Name: person uk_8mr53bm4ukh5yu19amqwqku3x; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT uk_8mr53bm4ukh5yu19amqwqku3x UNIQUE (app_user);


--
-- TOC entry 4733 (class 2606 OID 54430)
-- Name: doctor uk_9yt1of9hyayittqoxu8ws92py; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT uk_9yt1of9hyayittqoxu8ws92py UNIQUE (clinic_staff);


--
-- TOC entry 4745 (class 2606 OID 54434)
-- Name: lab_supervisor uk_kc30unn7bnrbbyrh1tsub196t; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_supervisor
    ADD CONSTRAINT uk_kc30unn7bnrbbyrh1tsub196t UNIQUE (lab_staff);


--
-- TOC entry 4749 (class 2606 OID 54436)
-- Name: lab_assistant uk_m8sfbbngqkukoaxtyts2y3c0t; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_assistant
    ADD CONSTRAINT uk_m8sfbbngqkukoaxtyts2y3c0t UNIQUE (lab_staff);


--
-- TOC entry 4753 (class 2606 OID 54438)
-- Name: patient uk_p0kfyft4h4a36aycdqmq8kvat; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT uk_p0kfyft4h4a36aycdqmq8kvat UNIQUE (person);


--
-- TOC entry 4767 (class 2606 OID 54444)
-- Name: refresh_token uk_r4k4edos30bx9neoq81mdvwph; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT uk_r4k4edos30bx9neoq81mdvwph UNIQUE (token);


--
-- TOC entry 4741 (class 2606 OID 54432)
-- Name: lab_staff uk_t9uarcy1xieow8are7tljhc4t; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_staff
    ADD CONSTRAINT uk_t9uarcy1xieow8are7tljhc4t UNIQUE (person);


--
-- TOC entry 4769 (class 2606 OID 54424)
-- Name: visit visit_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit
    ADD CONSTRAINT visit_pkey PRIMARY KEY (id);


--
-- TOC entry 4780 (class 2606 OID 54495)
-- Name: patient fk1qle78qg2u7w6lpb8xn9yhg3r; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT fk1qle78qg2u7w6lpb8xn9yhg3r FOREIGN KEY (person) REFERENCES public.person(nationalidnumber);


--
-- TOC entry 4773 (class 2606 OID 54465)
-- Name: lab_examination fk2vfq8gvpnnucxbft3n3w2xik8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_examination
    ADD CONSTRAINT fk2vfq8gvpnnucxbft3n3w2xik8 FOREIGN KEY (lab_assist) REFERENCES public.lab_assistant(lab_staff);


--
-- TOC entry 4782 (class 2606 OID 54510)
-- Name: physical_examination fk31y2lxc3wnmbdhw2ql1u1m75d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.physical_examination
    ADD CONSTRAINT fk31y2lxc3wnmbdhw2ql1u1m75d FOREIGN KEY (visit) REFERENCES public.visit(id);


--
-- TOC entry 4786 (class 2606 OID 54525)
-- Name: visit fk3tc2nk9pa4jcw4fewpfj25a1h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit
    ADD CONSTRAINT fk3tc2nk9pa4jcw4fewpfj25a1h FOREIGN KEY (patient) REFERENCES public.patient(insurance_id);


--
-- TOC entry 4770 (class 2606 OID 54445)
-- Name: basic_auth_user fk5wwq7nhbmvnfw0m2lp5ky444o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.basic_auth_user
    ADD CONSTRAINT fk5wwq7nhbmvnfw0m2lp5ky444o FOREIGN KEY (app_user) REFERENCES public.app_user(id);


--
-- TOC entry 4783 (class 2606 OID 54505)
-- Name: physical_examination fk5yq5ltrcy1oh4g2q4dma9sxk6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.physical_examination
    ADD CONSTRAINT fk5yq5ltrcy1oh4g2q4dma9sxk6 FOREIGN KEY (examination_dictionary) REFERENCES public.examination_dictionary(code);


--
-- TOC entry 4774 (class 2606 OID 54460)
-- Name: lab_examination fk7d79jkp5u2ju0kpu9koa7d4ue; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_examination
    ADD CONSTRAINT fk7d79jkp5u2ju0kpu9koa7d4ue FOREIGN KEY (examination_dictionary) REFERENCES public.examination_dictionary(code);


--
-- TOC entry 4784 (class 2606 OID 54515)
-- Name: receptionist fk8ypbno7x3ayxywwj39ux0h7k2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receptionist
    ADD CONSTRAINT fk8ypbno7x3ayxywwj39ux0h7k2 FOREIGN KEY (clinic_staff) REFERENCES public.clinic_staff(clinic_emp_id);


--
-- TOC entry 4778 (class 2606 OID 54485)
-- Name: lab_supervisor fkcwrcd0kc7n2cwk5ffgcf1dfqf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_supervisor
    ADD CONSTRAINT fkcwrcd0kc7n2cwk5ffgcf1dfqf FOREIGN KEY (lab_staff) REFERENCES public.lab_staff(lab_emp_id);


--
-- TOC entry 4772 (class 2606 OID 54455)
-- Name: doctor fklg5ynd33ejxf93hwgjc05rjiv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT fklg5ynd33ejxf93hwgjc05rjiv FOREIGN KEY (clinic_staff) REFERENCES public.clinic_staff(clinic_emp_id);


--
-- TOC entry 4775 (class 2606 OID 54470)
-- Name: lab_examination fklmlq369ff7kbtdrb3edyf4jhj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_examination
    ADD CONSTRAINT fklmlq369ff7kbtdrb3edyf4jhj FOREIGN KEY (visit) REFERENCES public.visit(id);


--
-- TOC entry 4787 (class 2606 OID 54530)
-- Name: visit fkm3wk9dybwm58u5hx9kujl1bgb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit
    ADD CONSTRAINT fkm3wk9dybwm58u5hx9kujl1bgb FOREIGN KEY (receptionist) REFERENCES public.receptionist(clinic_staff);


--
-- TOC entry 4781 (class 2606 OID 54500)
-- Name: person fkmebx5o59h6gqkdhetwnn332ct; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT fkmebx5o59h6gqkdhetwnn332ct FOREIGN KEY (app_user) REFERENCES public.app_user(id);


--
-- TOC entry 4788 (class 2606 OID 54535)
-- Name: visit fkmejokb226gd7beykc9jd11j7h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visit
    ADD CONSTRAINT fkmejokb226gd7beykc9jd11j7h FOREIGN KEY (selected_doctor) REFERENCES public.doctor(clinic_staff);


--
-- TOC entry 4779 (class 2606 OID 54490)
-- Name: lab_assistant fknkhte9fq95rglp0kno3m1trin; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_assistant
    ADD CONSTRAINT fknkhte9fq95rglp0kno3m1trin FOREIGN KEY (lab_staff) REFERENCES public.lab_staff(lab_emp_id);


--
-- TOC entry 4777 (class 2606 OID 54480)
-- Name: lab_staff fkpc19vhxqwg1q1k8in3nnhfy0j; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_staff
    ADD CONSTRAINT fkpc19vhxqwg1q1k8in3nnhfy0j FOREIGN KEY (person) REFERENCES public.person(nationalidnumber);


--
-- TOC entry 4771 (class 2606 OID 54450)
-- Name: clinic_staff fkrmsatonbnjynfsn4y6sch21v8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clinic_staff
    ADD CONSTRAINT fkrmsatonbnjynfsn4y6sch21v8 FOREIGN KEY (person) REFERENCES public.person(nationalidnumber);


--
-- TOC entry 4785 (class 2606 OID 54520)
-- Name: refresh_token fksqhr2ooxsl17ilcyuo4gl1r7c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT fksqhr2ooxsl17ilcyuo4gl1r7c FOREIGN KEY (app_user) REFERENCES public.app_user(id);


--
-- TOC entry 4776 (class 2606 OID 54475)
-- Name: lab_examination fksv4yw47ox93mw322dhr8v56am; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lab_examination
    ADD CONSTRAINT fksv4yw47ox93mw322dhr8v56am FOREIGN KEY (lab_super) REFERENCES public.lab_supervisor(lab_staff);


-- Completed on 2024-07-07 15:46:32

--
-- PostgreSQL database dump complete
--

