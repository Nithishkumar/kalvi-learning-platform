--
-- PostgreSQL database dump
--

-- Dumped from database version 14.11 (Homebrew)
-- Dumped by pg_dump version 16.0

-- Started on 2024-02-23 17:27:35 IST

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
-- Name: public; Type: SCHEMA; Schema: -; Owner: nithish
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO nithish;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 16896)
-- Name: assignment; Type: TABLE; Schema: public; Owner: nithish
--

CREATE TABLE public.assignment (
    course_id bigint,
    id bigint NOT NULL,
    assignment_title character varying(255)
);


ALTER TABLE public.assignment OWNER TO nithish;

--
-- TOC entry 209 (class 1259 OID 16895)
-- Name: assignment_id_seq; Type: SEQUENCE; Schema: public; Owner: nithish
--

CREATE SEQUENCE public.assignment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.assignment_id_seq OWNER TO nithish;

--
-- TOC entry 3736 (class 0 OID 0)
-- Dependencies: 209
-- Name: assignment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nithish
--

ALTER SEQUENCE public.assignment_id_seq OWNED BY public.assignment.id;


--
-- TOC entry 212 (class 1259 OID 16903)
-- Name: assignment_progress; Type: TABLE; Schema: public; Owner: nithish
--

CREATE TABLE public.assignment_progress (
    submitted boolean NOT NULL,
    assignment_id bigint,
    id bigint NOT NULL,
    student_progress_id bigint
);


ALTER TABLE public.assignment_progress OWNER TO nithish;

--
-- TOC entry 211 (class 1259 OID 16902)
-- Name: assignment_progress_id_seq; Type: SEQUENCE; Schema: public; Owner: nithish
--

CREATE SEQUENCE public.assignment_progress_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.assignment_progress_id_seq OWNER TO nithish;

--
-- TOC entry 3737 (class 0 OID 0)
-- Dependencies: 211
-- Name: assignment_progress_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nithish
--

ALTER SEQUENCE public.assignment_progress_id_seq OWNED BY public.assignment_progress.id;


--
-- TOC entry 214 (class 1259 OID 16910)
-- Name: course; Type: TABLE; Schema: public; Owner: nithish
--

CREATE TABLE public.course (
    price double precision NOT NULL,
    created_at timestamp(6) without time zone,
    id bigint NOT NULL,
    last_updated timestamp(6) without time zone,
    course_name character varying(255),
    created_by character varying(255),
    description oid
);


ALTER TABLE public.course OWNER TO nithish;

--
-- TOC entry 213 (class 1259 OID 16909)
-- Name: course_id_seq; Type: SEQUENCE; Schema: public; Owner: nithish
--

CREATE SEQUENCE public.course_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.course_id_seq OWNER TO nithish;

--
-- TOC entry 3738 (class 0 OID 0)
-- Dependencies: 213
-- Name: course_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nithish
--

ALTER SEQUENCE public.course_id_seq OWNED BY public.course.id;


--
-- TOC entry 215 (class 1259 OID 16918)
-- Name: course_languages; Type: TABLE; Schema: public; Owner: nithish
--

CREATE TABLE public.course_languages (
    course_id bigint NOT NULL,
    languages character varying(255)
);


ALTER TABLE public.course_languages OWNER TO nithish;

--
-- TOC entry 217 (class 1259 OID 16922)
-- Name: module; Type: TABLE; Schema: public; Owner: nithish
--

CREATE TABLE public.module (
    duration integer NOT NULL,
    course_id bigint,
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.module OWNER TO nithish;

--
-- TOC entry 216 (class 1259 OID 16921)
-- Name: module_id_seq; Type: SEQUENCE; Schema: public; Owner: nithish
--

CREATE SEQUENCE public.module_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.module_id_seq OWNER TO nithish;

--
-- TOC entry 3739 (class 0 OID 0)
-- Dependencies: 216
-- Name: module_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nithish
--

ALTER SEQUENCE public.module_id_seq OWNED BY public.module.id;


--
-- TOC entry 219 (class 1259 OID 16929)
-- Name: quiz; Type: TABLE; Schema: public; Owner: nithish
--

CREATE TABLE public.quiz (
    course_id bigint,
    id bigint NOT NULL,
    title character varying(255)
);


ALTER TABLE public.quiz OWNER TO nithish;

--
-- TOC entry 218 (class 1259 OID 16928)
-- Name: quiz_id_seq; Type: SEQUENCE; Schema: public; Owner: nithish
--

CREATE SEQUENCE public.quiz_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.quiz_id_seq OWNER TO nithish;

--
-- TOC entry 3740 (class 0 OID 0)
-- Dependencies: 218
-- Name: quiz_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nithish
--

ALTER SEQUENCE public.quiz_id_seq OWNED BY public.quiz.id;


--
-- TOC entry 222 (class 1259 OID 16941)
-- Name: quiz_progress; Type: TABLE; Schema: public; Owner: nithish
--

CREATE TABLE public.quiz_progress (
    score integer NOT NULL,
    id bigint NOT NULL,
    quiz_id bigint,
    student_progress_id bigint
);


ALTER TABLE public.quiz_progress OWNER TO nithish;

--
-- TOC entry 221 (class 1259 OID 16940)
-- Name: quiz_progress_id_seq; Type: SEQUENCE; Schema: public; Owner: nithish
--

CREATE SEQUENCE public.quiz_progress_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.quiz_progress_id_seq OWNER TO nithish;

--
-- TOC entry 3741 (class 0 OID 0)
-- Dependencies: 221
-- Name: quiz_progress_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nithish
--

ALTER SEQUENCE public.quiz_progress_id_seq OWNED BY public.quiz_progress.id;


--
-- TOC entry 220 (class 1259 OID 16935)
-- Name: quiz_questions; Type: TABLE; Schema: public; Owner: nithish
--

CREATE TABLE public.quiz_questions (
    quiz_id bigint NOT NULL,
    correct_answer character varying(255),
    question_text character varying(255),
    options character varying(255)[]
);


ALTER TABLE public.quiz_questions OWNER TO nithish;

--
-- TOC entry 224 (class 1259 OID 16948)
-- Name: rating; Type: TABLE; Schema: public; Owner: nithish
--

CREATE TABLE public.rating (
    rating integer NOT NULL,
    course_id bigint,
    created_at timestamp(6) without time zone,
    id bigint NOT NULL
);


ALTER TABLE public.rating OWNER TO nithish;

--
-- TOC entry 223 (class 1259 OID 16947)
-- Name: rating_id_seq; Type: SEQUENCE; Schema: public; Owner: nithish
--

CREATE SEQUENCE public.rating_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.rating_id_seq OWNER TO nithish;

--
-- TOC entry 3742 (class 0 OID 0)
-- Dependencies: 223
-- Name: rating_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nithish
--

ALTER SEQUENCE public.rating_id_seq OWNED BY public.rating.id;


--
-- TOC entry 226 (class 1259 OID 16955)
-- Name: student; Type: TABLE; Schema: public; Owner: nithish
--

CREATE TABLE public.student (
    id bigint NOT NULL,
    address character varying(255),
    name character varying(255),
    password character varying(255),
    profile_picture character varying(255),
    username character varying(255)
);


ALTER TABLE public.student OWNER TO nithish;

--
-- TOC entry 227 (class 1259 OID 16963)
-- Name: student_course; Type: TABLE; Schema: public; Owner: nithish
--

CREATE TABLE public.student_course (
    course_id bigint NOT NULL,
    student_id bigint NOT NULL
);


ALTER TABLE public.student_course OWNER TO nithish;

--
-- TOC entry 225 (class 1259 OID 16954)
-- Name: student_id_seq; Type: SEQUENCE; Schema: public; Owner: nithish
--

CREATE SEQUENCE public.student_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.student_id_seq OWNER TO nithish;

--
-- TOC entry 3743 (class 0 OID 0)
-- Dependencies: 225
-- Name: student_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nithish
--

ALTER SEQUENCE public.student_id_seq OWNED BY public.student.id;


--
-- TOC entry 229 (class 1259 OID 16969)
-- Name: student_progress; Type: TABLE; Schema: public; Owner: nithish
--

CREATE TABLE public.student_progress (
    course_id bigint,
    id bigint NOT NULL,
    student_id bigint
);


ALTER TABLE public.student_progress OWNER TO nithish;

--
-- TOC entry 228 (class 1259 OID 16968)
-- Name: student_progress_id_seq; Type: SEQUENCE; Schema: public; Owner: nithish
--

CREATE SEQUENCE public.student_progress_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.student_progress_id_seq OWNER TO nithish;

--
-- TOC entry 3744 (class 0 OID 0)
-- Dependencies: 228
-- Name: student_progress_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nithish
--

ALTER SEQUENCE public.student_progress_id_seq OWNED BY public.student_progress.id;


--
-- TOC entry 231 (class 1259 OID 16976)
-- Name: topic; Type: TABLE; Schema: public; Owner: nithish
--

CREATE TABLE public.topic (
    id bigint NOT NULL,
    module_id bigint,
    name character varying(255)
);


ALTER TABLE public.topic OWNER TO nithish;

--
-- TOC entry 232 (class 1259 OID 16982)
-- Name: topic_data; Type: TABLE; Schema: public; Owner: nithish
--

CREATE TABLE public.topic_data (
    topic_id bigint NOT NULL,
    data character varying(255)
);


ALTER TABLE public.topic_data OWNER TO nithish;

--
-- TOC entry 230 (class 1259 OID 16975)
-- Name: topic_id_seq; Type: SEQUENCE; Schema: public; Owner: nithish
--

CREATE SEQUENCE public.topic_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.topic_id_seq OWNER TO nithish;

--
-- TOC entry 3745 (class 0 OID 0)
-- Dependencies: 230
-- Name: topic_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nithish
--

ALTER SEQUENCE public.topic_id_seq OWNED BY public.topic.id;


--
-- TOC entry 233 (class 1259 OID 16985)
-- Name: topic_video_urls; Type: TABLE; Schema: public; Owner: nithish
--

CREATE TABLE public.topic_video_urls (
    topic_id bigint NOT NULL,
    video_urls character varying(255)
);


ALTER TABLE public.topic_video_urls OWNER TO nithish;

--
-- TOC entry 3517 (class 2604 OID 16899)
-- Name: assignment id; Type: DEFAULT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.assignment ALTER COLUMN id SET DEFAULT nextval('public.assignment_id_seq'::regclass);


--
-- TOC entry 3518 (class 2604 OID 16906)
-- Name: assignment_progress id; Type: DEFAULT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.assignment_progress ALTER COLUMN id SET DEFAULT nextval('public.assignment_progress_id_seq'::regclass);


--
-- TOC entry 3519 (class 2604 OID 16913)
-- Name: course id; Type: DEFAULT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.course ALTER COLUMN id SET DEFAULT nextval('public.course_id_seq'::regclass);


--
-- TOC entry 3520 (class 2604 OID 16925)
-- Name: module id; Type: DEFAULT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.module ALTER COLUMN id SET DEFAULT nextval('public.module_id_seq'::regclass);


--
-- TOC entry 3521 (class 2604 OID 16932)
-- Name: quiz id; Type: DEFAULT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.quiz ALTER COLUMN id SET DEFAULT nextval('public.quiz_id_seq'::regclass);


--
-- TOC entry 3522 (class 2604 OID 16944)
-- Name: quiz_progress id; Type: DEFAULT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.quiz_progress ALTER COLUMN id SET DEFAULT nextval('public.quiz_progress_id_seq'::regclass);


--
-- TOC entry 3523 (class 2604 OID 16951)
-- Name: rating id; Type: DEFAULT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.rating ALTER COLUMN id SET DEFAULT nextval('public.rating_id_seq'::regclass);


--
-- TOC entry 3524 (class 2604 OID 16958)
-- Name: student id; Type: DEFAULT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.student ALTER COLUMN id SET DEFAULT nextval('public.student_id_seq'::regclass);


--
-- TOC entry 3525 (class 2604 OID 16972)
-- Name: student_progress id; Type: DEFAULT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.student_progress ALTER COLUMN id SET DEFAULT nextval('public.student_progress_id_seq'::regclass);


--
-- TOC entry 3526 (class 2604 OID 16979)
-- Name: topic id; Type: DEFAULT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.topic ALTER COLUMN id SET DEFAULT nextval('public.topic_id_seq'::regclass);


--
-- TOC entry 3706 (class 0 OID 16896)
-- Dependencies: 210
-- Data for Name: assignment; Type: TABLE DATA; Schema: public; Owner: nithish
--

COPY public.assignment (course_id, id, assignment_title) FROM stdin;
\.


--
-- TOC entry 3708 (class 0 OID 16903)
-- Dependencies: 212
-- Data for Name: assignment_progress; Type: TABLE DATA; Schema: public; Owner: nithish
--

COPY public.assignment_progress (submitted, assignment_id, id, student_progress_id) FROM stdin;
\.


--
-- TOC entry 3710 (class 0 OID 16910)
-- Dependencies: 214
-- Data for Name: course; Type: TABLE DATA; Schema: public; Owner: nithish
--

COPY public.course (price, created_at, id, last_updated, course_name, created_by, description) FROM stdin;
\.


--
-- TOC entry 3711 (class 0 OID 16918)
-- Dependencies: 215
-- Data for Name: course_languages; Type: TABLE DATA; Schema: public; Owner: nithish
--

COPY public.course_languages (course_id, languages) FROM stdin;
\.


--
-- TOC entry 3713 (class 0 OID 16922)
-- Dependencies: 217
-- Data for Name: module; Type: TABLE DATA; Schema: public; Owner: nithish
--

COPY public.module (duration, course_id, id, name) FROM stdin;
\.


--
-- TOC entry 3715 (class 0 OID 16929)
-- Dependencies: 219
-- Data for Name: quiz; Type: TABLE DATA; Schema: public; Owner: nithish
--

COPY public.quiz (course_id, id, title) FROM stdin;
\.


--
-- TOC entry 3718 (class 0 OID 16941)
-- Dependencies: 222
-- Data for Name: quiz_progress; Type: TABLE DATA; Schema: public; Owner: nithish
--

COPY public.quiz_progress (score, id, quiz_id, student_progress_id) FROM stdin;
\.


--
-- TOC entry 3716 (class 0 OID 16935)
-- Dependencies: 220
-- Data for Name: quiz_questions; Type: TABLE DATA; Schema: public; Owner: nithish
--

COPY public.quiz_questions (quiz_id, correct_answer, question_text, options) FROM stdin;
\.


--
-- TOC entry 3720 (class 0 OID 16948)
-- Dependencies: 224
-- Data for Name: rating; Type: TABLE DATA; Schema: public; Owner: nithish
--

COPY public.rating (rating, course_id, created_at, id) FROM stdin;
\.


--
-- TOC entry 3722 (class 0 OID 16955)
-- Dependencies: 226
-- Data for Name: student; Type: TABLE DATA; Schema: public; Owner: nithish
--

COPY public.student (id, address, name, password, profile_picture, username) FROM stdin;
\.


--
-- TOC entry 3723 (class 0 OID 16963)
-- Dependencies: 227
-- Data for Name: student_course; Type: TABLE DATA; Schema: public; Owner: nithish
--

COPY public.student_course (course_id, student_id) FROM stdin;
\.


--
-- TOC entry 3725 (class 0 OID 16969)
-- Dependencies: 229
-- Data for Name: student_progress; Type: TABLE DATA; Schema: public; Owner: nithish
--

COPY public.student_progress (course_id, id, student_id) FROM stdin;
\.


--
-- TOC entry 3727 (class 0 OID 16976)
-- Dependencies: 231
-- Data for Name: topic; Type: TABLE DATA; Schema: public; Owner: nithish
--

COPY public.topic (id, module_id, name) FROM stdin;
\.


--
-- TOC entry 3728 (class 0 OID 16982)
-- Dependencies: 232
-- Data for Name: topic_data; Type: TABLE DATA; Schema: public; Owner: nithish
--

COPY public.topic_data (topic_id, data) FROM stdin;
\.


--
-- TOC entry 3729 (class 0 OID 16985)
-- Dependencies: 233
-- Data for Name: topic_video_urls; Type: TABLE DATA; Schema: public; Owner: nithish
--

COPY public.topic_video_urls (topic_id, video_urls) FROM stdin;
\.


--
-- TOC entry 3746 (class 0 OID 0)
-- Dependencies: 209
-- Name: assignment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nithish
--

SELECT pg_catalog.setval('public.assignment_id_seq', 1, false);


--
-- TOC entry 3747 (class 0 OID 0)
-- Dependencies: 211
-- Name: assignment_progress_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nithish
--

SELECT pg_catalog.setval('public.assignment_progress_id_seq', 1, false);


--
-- TOC entry 3748 (class 0 OID 0)
-- Dependencies: 213
-- Name: course_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nithish
--

SELECT pg_catalog.setval('public.course_id_seq', 1, false);


--
-- TOC entry 3749 (class 0 OID 0)
-- Dependencies: 216
-- Name: module_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nithish
--

SELECT pg_catalog.setval('public.module_id_seq', 1, false);


--
-- TOC entry 3750 (class 0 OID 0)
-- Dependencies: 218
-- Name: quiz_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nithish
--

SELECT pg_catalog.setval('public.quiz_id_seq', 1, false);


--
-- TOC entry 3751 (class 0 OID 0)
-- Dependencies: 221
-- Name: quiz_progress_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nithish
--

SELECT pg_catalog.setval('public.quiz_progress_id_seq', 1, false);


--
-- TOC entry 3752 (class 0 OID 0)
-- Dependencies: 223
-- Name: rating_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nithish
--

SELECT pg_catalog.setval('public.rating_id_seq', 1, false);


--
-- TOC entry 3753 (class 0 OID 0)
-- Dependencies: 225
-- Name: student_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nithish
--

SELECT pg_catalog.setval('public.student_id_seq', 1, false);


--
-- TOC entry 3754 (class 0 OID 0)
-- Dependencies: 228
-- Name: student_progress_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nithish
--

SELECT pg_catalog.setval('public.student_progress_id_seq', 1, false);


--
-- TOC entry 3755 (class 0 OID 0)
-- Dependencies: 230
-- Name: topic_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nithish
--

SELECT pg_catalog.setval('public.topic_id_seq', 1, false);


--
-- TOC entry 3528 (class 2606 OID 16901)
-- Name: assignment assignment_pkey; Type: CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.assignment
    ADD CONSTRAINT assignment_pkey PRIMARY KEY (id);


--
-- TOC entry 3530 (class 2606 OID 16908)
-- Name: assignment_progress assignment_progress_pkey; Type: CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.assignment_progress
    ADD CONSTRAINT assignment_progress_pkey PRIMARY KEY (id);


--
-- TOC entry 3532 (class 2606 OID 16917)
-- Name: course course_pkey; Type: CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.course
    ADD CONSTRAINT course_pkey PRIMARY KEY (id);


--
-- TOC entry 3534 (class 2606 OID 16927)
-- Name: module module_pkey; Type: CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.module
    ADD CONSTRAINT module_pkey PRIMARY KEY (id);


--
-- TOC entry 3536 (class 2606 OID 16934)
-- Name: quiz quiz_pkey; Type: CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.quiz
    ADD CONSTRAINT quiz_pkey PRIMARY KEY (id);


--
-- TOC entry 3538 (class 2606 OID 16946)
-- Name: quiz_progress quiz_progress_pkey; Type: CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.quiz_progress
    ADD CONSTRAINT quiz_progress_pkey PRIMARY KEY (id);


--
-- TOC entry 3540 (class 2606 OID 16953)
-- Name: rating rating_pkey; Type: CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.rating
    ADD CONSTRAINT rating_pkey PRIMARY KEY (id);


--
-- TOC entry 3544 (class 2606 OID 16967)
-- Name: student_course student_course_pkey; Type: CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.student_course
    ADD CONSTRAINT student_course_pkey PRIMARY KEY (course_id, student_id);


--
-- TOC entry 3542 (class 2606 OID 16962)
-- Name: student student_pkey; Type: CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_pkey PRIMARY KEY (id);


--
-- TOC entry 3546 (class 2606 OID 16974)
-- Name: student_progress student_progress_pkey; Type: CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.student_progress
    ADD CONSTRAINT student_progress_pkey PRIMARY KEY (id);


--
-- TOC entry 3548 (class 2606 OID 16981)
-- Name: topic topic_pkey; Type: CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.topic
    ADD CONSTRAINT topic_pkey PRIMARY KEY (id);


--
-- TOC entry 3564 (class 2606 OID 17063)
-- Name: topic_data fk1ew26fluj5rumo7ngc7baxxuq; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.topic_data
    ADD CONSTRAINT fk1ew26fluj5rumo7ngc7baxxuq FOREIGN KEY (topic_id) REFERENCES public.topic(id);


--
-- TOC entry 3563 (class 2606 OID 17058)
-- Name: topic fk61tp1ugtn26t353ss9nwaj41k; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.topic
    ADD CONSTRAINT fk61tp1ugtn26t353ss9nwaj41k FOREIGN KEY (module_id) REFERENCES public.module(id);


--
-- TOC entry 3565 (class 2606 OID 17068)
-- Name: topic_video_urls fk68jjkmucut5e6dw9fpqn9sohn; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.topic_video_urls
    ADD CONSTRAINT fk68jjkmucut5e6dw9fpqn9sohn FOREIGN KEY (topic_id) REFERENCES public.topic(id);


--
-- TOC entry 3550 (class 2606 OID 16993)
-- Name: assignment_progress fk7o6abukma83ku3xrge9sy0qnr; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.assignment_progress
    ADD CONSTRAINT fk7o6abukma83ku3xrge9sy0qnr FOREIGN KEY (assignment_id) REFERENCES public.assignment(id);


--
-- TOC entry 3556 (class 2606 OID 17023)
-- Name: quiz_progress fk9f7ju88y7xqvqk9fb360ascnr; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.quiz_progress
    ADD CONSTRAINT fk9f7ju88y7xqvqk9fb360ascnr FOREIGN KEY (quiz_id) REFERENCES public.quiz(id);


--
-- TOC entry 3551 (class 2606 OID 16998)
-- Name: assignment_progress fkc8rm43srb907i6sem5bue6mtb; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.assignment_progress
    ADD CONSTRAINT fkc8rm43srb907i6sem5bue6mtb FOREIGN KEY (student_progress_id) REFERENCES public.student_progress(id);


--
-- TOC entry 3554 (class 2606 OID 17013)
-- Name: quiz fkce16mrsgeokucc022mpyev7xk; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.quiz
    ADD CONSTRAINT fkce16mrsgeokucc022mpyev7xk FOREIGN KEY (course_id) REFERENCES public.course(id);


--
-- TOC entry 3555 (class 2606 OID 17018)
-- Name: quiz_questions fkcgp9e1c6ww3t383aui4w8feae; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.quiz_questions
    ADD CONSTRAINT fkcgp9e1c6ww3t383aui4w8feae FOREIGN KEY (quiz_id) REFERENCES public.quiz(id);


--
-- TOC entry 3559 (class 2606 OID 17038)
-- Name: student_course fkejrkh4gv8iqgmspsanaji90ws; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.student_course
    ADD CONSTRAINT fkejrkh4gv8iqgmspsanaji90ws FOREIGN KEY (course_id) REFERENCES public.course(id);


--
-- TOC entry 3553 (class 2606 OID 17008)
-- Name: module fkfq09oddpwjoxcirvkh9vnfnsg; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.module
    ADD CONSTRAINT fkfq09oddpwjoxcirvkh9vnfnsg FOREIGN KEY (course_id) REFERENCES public.course(id);


--
-- TOC entry 3561 (class 2606 OID 17048)
-- Name: student_progress fkgs6u4am5j5c7o5gcb24cv8sdd; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.student_progress
    ADD CONSTRAINT fkgs6u4am5j5c7o5gcb24cv8sdd FOREIGN KEY (course_id) REFERENCES public.course(id);


--
-- TOC entry 3562 (class 2606 OID 17053)
-- Name: student_progress fkl0cwsyxx6m6x6ooi0wmg07w5r; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.student_progress
    ADD CONSTRAINT fkl0cwsyxx6m6x6ooi0wmg07w5r FOREIGN KEY (student_id) REFERENCES public.student(id);


--
-- TOC entry 3557 (class 2606 OID 17028)
-- Name: quiz_progress fkmvliigawrb876qia7bscaqlql; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.quiz_progress
    ADD CONSTRAINT fkmvliigawrb876qia7bscaqlql FOREIGN KEY (student_progress_id) REFERENCES public.student_progress(id);


--
-- TOC entry 3552 (class 2606 OID 17003)
-- Name: course_languages fkpdo5obx1sfcr6stkdu9549cbs; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.course_languages
    ADD CONSTRAINT fkpdo5obx1sfcr6stkdu9549cbs FOREIGN KEY (course_id) REFERENCES public.course(id);


--
-- TOC entry 3560 (class 2606 OID 17043)
-- Name: student_course fkq7yw2wg9wlt2cnj480hcdn6dq; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.student_course
    ADD CONSTRAINT fkq7yw2wg9wlt2cnj480hcdn6dq FOREIGN KEY (student_id) REFERENCES public.student(id);


--
-- TOC entry 3558 (class 2606 OID 17033)
-- Name: rating fkrbuqjo7wyi9w281uaeupnk26m; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.rating
    ADD CONSTRAINT fkrbuqjo7wyi9w281uaeupnk26m FOREIGN KEY (course_id) REFERENCES public.course(id);


--
-- TOC entry 3549 (class 2606 OID 16988)
-- Name: assignment fkrop26uwnbkstbtfha3ormxp85; Type: FK CONSTRAINT; Schema: public; Owner: nithish
--

ALTER TABLE ONLY public.assignment
    ADD CONSTRAINT fkrop26uwnbkstbtfha3ormxp85 FOREIGN KEY (course_id) REFERENCES public.course(id);


--
-- TOC entry 3735 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: nithish
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2024-02-23 17:27:35 IST

--
-- PostgreSQL database dump complete
--

