/*
Navicat PGSQL Data Transfer

Source Server         : localhost_5432
Source Server Version : 90310
Source Host           : localhost:5432
Source Database       : projectx
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90310
File Encoding         : 65001

Date: 2018-04-25 01:31:45
*/


-- ----------------------------
-- Sequence structure for clans_id_seq
-- ----------------------------
DROP SEQUENCE "public"."clans_id_seq";
CREATE SEQUENCE "public"."clans_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
SELECT setval('"public"."clans_id_seq"', 1, true);

-- ----------------------------
-- Sequence structure for contas_seq
-- ----------------------------
DROP SEQUENCE "public"."contas_seq";
CREATE SEQUENCE "public"."contas_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
SELECT setval('"public"."contas_seq"', 1, true);

-- ----------------------------
-- Sequence structure for ipsystem_seq
-- ----------------------------
DROP SEQUENCE "public"."ipsystem_seq";
CREATE SEQUENCE "public"."ipsystem_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for jogador_inventario_seq
-- ----------------------------
DROP SEQUENCE "public"."jogador_inventario_seq";
CREATE SEQUENCE "public"."jogador_inventario_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
SELECT setval('"public"."jogador_inventario_seq"', 1, true);

-- ----------------------------
-- Sequence structure for jogador_mensagem_seq
-- ----------------------------
DROP SEQUENCE "public"."jogador_mensagem_seq";
CREATE SEQUENCE "public"."jogador_mensagem_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
SELECT setval('"public"."jogador_mensagem_seq"', 1, true);

-- ----------------------------
-- Sequence structure for loja_seq
-- ----------------------------
DROP SEQUENCE "public"."loja_seq";
CREATE SEQUENCE "public"."loja_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 2
 CACHE 1;
SELECT setval('"public"."loja_seq"', 2, true);

-- ----------------------------
-- Sequence structure for storage_seq
-- ----------------------------
DROP SEQUENCE "public"."storage_seq";
CREATE SEQUENCE "public"."storage_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 25
 CACHE 1;
SELECT setval('"public"."storage_seq"', 25, true);

-- ----------------------------
-- Table structure for contas
-- ----------------------------
DROP TABLE IF EXISTS "public"."contas";
CREATE TABLE "public"."contas" (
"id" int8 DEFAULT nextval('contas_seq'::regclass) NOT NULL,
"login" varchar(255) COLLATE "default" DEFAULT ''::character varying NOT NULL,
"senha" varchar(255) COLLATE "default" DEFAULT ''::character varying NOT NULL,
"ip" varchar(32) COLLATE "default" DEFAULT ''::character varying NOT NULL,
"mac" varchar(32) COLLATE "default" DEFAULT ''::character varying NOT NULL,
"last_access" varchar(255) COLLATE "default" DEFAULT ''::character varying NOT NULL,
"client_version" varchar COLLATE "default" DEFAULT ''::character varying NOT NULL,
"ban_expires" int4 DEFAULT 0 NOT NULL,
"userfilelist" varchar COLLATE "default" DEFAULT ''::character varying NOT NULL,
"launcher_key" int8 DEFAULT 0 NOT NULL,
"email" varchar COLLATE "default" DEFAULT ''::character varying NOT NULL,
"bans" int4 DEFAULT 0 NOT NULL,
"hwid" varchar(255) COLLATE "default" DEFAULT ''::character varying NOT NULL,
"last_port" int4 DEFAULT 0 NOT NULL,
"actived" bool DEFAULT true NOT NULL,
"uniqueid" varchar(250) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of contas
-- ----------------------------

-- ----------------------------
-- Table structure for cupom
-- ----------------------------
DROP TABLE IF EXISTS "public"."cupom";
CREATE TABLE "public"."cupom" (
"id" int4,
"codigo" varchar(255) COLLATE "default",
"moeda" int4,
"status" int2
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of cupom
-- ----------------------------

-- ----------------------------
-- Table structure for jogador
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador";
CREATE TABLE "public"."jogador" (
"id" int8 NOT NULL,
"nick" varchar(255) COLLATE "default" DEFAULT ''::character varying,
"rank" int4 DEFAULT 0 NOT NULL,
"gold" int4 DEFAULT 80000 NOT NULL,
"cash" int4 DEFAULT 30000 NOT NULL,
"exp" int4 DEFAULT 0 NOT NULL,
"color" int4 DEFAULT 0 NOT NULL,
"clan_id" int4 DEFAULT 0 NOT NULL,
"brooch" int4 DEFAULT 0 NOT NULL,
"insignia" int4 DEFAULT 0 NOT NULL,
"medal" int4 DEFAULT 0 NOT NULL,
"blue_order" int4 DEFAULT 0 NOT NULL,
"mission1" int4 DEFAULT 1 NOT NULL,
"mission2" int4 DEFAULT 0 NOT NULL,
"mission3" int4 DEFAULT 0 NOT NULL,
"mission4" int4 DEFAULT 0 NOT NULL,
"tourney_level" int4 DEFAULT 0 NOT NULL,
"clan_date" int4 DEFAULT 0 NOT NULL,
"access_level" int4 DEFAULT 0 NOT NULL,
"role" int4 DEFAULT 0 NOT NULL,
"online" int4 DEFAULT 1 NOT NULL,
"last_up" int4 DEFAULT 1010000 NOT NULL,
"country" int4 DEFAULT 31,
"clan_invited" int4 DEFAULT 0
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_amigo
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_amigo";
CREATE TABLE "public"."jogador_amigo" (
"player_id" int8 NOT NULL,
"friend_id" int8,
"status" int4
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_amigo
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_bonus
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_bonus";
CREATE TABLE "public"."jogador_bonus" (
"player_id" int8 NOT NULL,
"event_id" int4 DEFAULT 0 NOT NULL,
"gift" int4 DEFAULT 0 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_bonus
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_clan
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_clan";
CREATE TABLE "public"."jogador_clan" (
"id" int4 DEFAULT nextval('clans_id_seq'::regclass) NOT NULL,
"owner" int8 NOT NULL,
"name" varchar COLLATE "default" DEFAULT ''::character varying NOT NULL,
"notice" varchar COLLATE "default" DEFAULT ''::character varying NOT NULL,
"info" varchar COLLATE "default" DEFAULT ''::character varying NOT NULL,
"rank" int4 DEFAULT 0,
"logo" int4 DEFAULT 0,
"color" int4 DEFAULT 0,
"partidas" int4 DEFAULT 0,
"vitorias" int4 DEFAULT 0,
"derrotas" int4 DEFAULT 0,
"autoridade" int4 DEFAULT 0,
"limite_rank" int4 DEFAULT 0,
"limite_idade" int4 DEFAULT 0,
"limite_idade2" int4 DEFAULT 0,
"pontos" int4 DEFAULT 1000,
"vagas" int4 DEFAULT 50,
"exp" int4 DEFAULT 0,
"data" int4 DEFAULT 0,
"player_vitorias" int8 DEFAULT 0,
"player_matou" int8 DEFAULT 0,
"player_headshots" int8 DEFAULT 0,
"player_exp" int8 DEFAULT 0,
"player_participacao" int8 DEFAULT 0,
"url" varchar COLLATE "default" DEFAULT ''::character varying
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_clan
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_config";
CREATE TABLE "public"."jogador_config" (
"player_id" int8 NOT NULL,
"config" int4 NOT NULL,
"sangue" int4 NOT NULL,
"mira" int4 NOT NULL,
"mao" int4 NOT NULL,
"audio1" int4 NOT NULL,
"audio2" int4 NOT NULL,
"audio_enable" int4 NOT NULL,
"sensibilidade" int4 NOT NULL,
"visao" int4 NOT NULL,
"mouse_invertido" int4 NOT NULL,
"msgconvite" int4 NOT NULL,
"chatsusurro" int4 NOT NULL,
"macro" int4 NOT NULL,
"unk1" int4 DEFAULT 0 NOT NULL,
"unk2" int4 DEFAULT 0 NOT NULL,
"unk3" int4 DEFAULT 0 NOT NULL,
"unk4" int4 DEFAULT 0 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_config
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_coupon
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_coupon";
CREATE TABLE "public"."jogador_coupon" (
"player_id" int8 NOT NULL,
"cor_mira" int4 NOT NULL,
"false_rank" int4 NOT NULL,
"false_nick" varchar(255) COLLATE "default" DEFAULT ''::character varying NOT NULL,
"cor_chat" int4 DEFAULT 0 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_coupon
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_equipamento
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_equipamento";
CREATE TABLE "public"."jogador_equipamento" (
"player_id" int8 NOT NULL,
"weapon_primary" int4 NOT NULL,
"weapon_secundary" int4 NOT NULL,
"weapon_melee" int4 NOT NULL,
"weapon_grenade" int4 NOT NULL,
"weapon_special" int4 NOT NULL,
"char_red" int4 NOT NULL,
"char_blue" int4 NOT NULL,
"char_head" int4 NOT NULL,
"char_beret" int4 NOT NULL,
"char_dino" int4 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_equipamento
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_estatisticas
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_estatisticas";
CREATE TABLE "public"."jogador_estatisticas" (
"player_id" int8 NOT NULL,
"partidas" int4 NOT NULL,
"ganhou" int4 NOT NULL,
"perdeu" int4 NOT NULL,
"matou" int4 NOT NULL,
"morreu" int4 NOT NULL,
"headshots" int4 NOT NULL,
"kitou" int4 NOT NULL,
"empatou" int4 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_estatisticas
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_evento
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_evento";
CREATE TABLE "public"."jogador_evento" (
"player_id" int8 NOT NULL,
"check_day" int4 NOT NULL,
"played" int4 NOT NULL,
"play_day" int4 NOT NULL,
"christmas" int4 DEFAULT 0 NOT NULL,
"present_day" int4 DEFAULT 0 NOT NULL,
"checks_id" int4 DEFAULT 0 NOT NULL,
"last_check1" int4 DEFAULT 0 NOT NULL,
"last_check2" int4 DEFAULT 0 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_evento
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_inventario
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_inventario";
CREATE TABLE "public"."jogador_inventario" (
"object" int8 DEFAULT nextval('jogador_inventario_seq'::regclass) NOT NULL,
"player_id" int8 NOT NULL,
"item_id" int4 NOT NULL,
"count" int4 NOT NULL,
"equip" int4 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_inventario
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_invites
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_invites";
CREATE TABLE "public"."jogador_invites" (
"clan_id" int4 NOT NULL,
"player_id" int8 NOT NULL,
"date" int4 NOT NULL,
"texto" varchar COLLATE "default" DEFAULT ''::character varying
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_invites
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_mensagem
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_mensagem";
CREATE TABLE "public"."jogador_mensagem" (
"object" int4 DEFAULT nextval('jogador_mensagem_seq'::regclass) NOT NULL,
"player_id" int8 NOT NULL,
"owner_id" int8 NOT NULL,
"recipient_name" varchar COLLATE "default" DEFAULT ''::character varying NOT NULL,
"texto" varchar COLLATE "default" DEFAULT ''::character varying,
"type" int4 NOT NULL,
"readed" int4 NOT NULL,
"expirate" int4 NOT NULL,
"dias" int4 NOT NULL,
"response" int4 DEFAULT 0 NOT NULL,
"receive" int4 DEFAULT 0 NOT NULL,
"owner_name" varchar COLLATE "default" DEFAULT ''::character varying NOT NULL,
"special" bool DEFAULT false
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_mensagem
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_missoes
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_missoes";
CREATE TABLE "public"."jogador_missoes" (
"player_id" int8 DEFAULT 0 NOT NULL,
"mission1_1" int4 DEFAULT 0 NOT NULL,
"mission1_2" int4 DEFAULT 0 NOT NULL,
"mission1_3" int4 DEFAULT 0 NOT NULL,
"mission1_4" int4 DEFAULT 0 NOT NULL,
"mission1_5" int4 DEFAULT 0 NOT NULL,
"mission1_6" int4 DEFAULT 0 NOT NULL,
"mission1_7" int4 DEFAULT 0 NOT NULL,
"mission1_8" int4 DEFAULT 0 NOT NULL,
"mission1_9" int4 DEFAULT 0 NOT NULL,
"mission1_10" int4 DEFAULT 0 NOT NULL,
"mission1_11" int4 DEFAULT 0 NOT NULL,
"mission1_12" int4 DEFAULT 0 NOT NULL,
"mission1_13" int4 DEFAULT 0 NOT NULL,
"mission1_14" int4 DEFAULT 0 NOT NULL,
"mission1_15" int4 DEFAULT 0 NOT NULL,
"mission1_16" int4 DEFAULT 0 NOT NULL,
"mission1_17" int4 DEFAULT 0 NOT NULL,
"mission1_18" int4 DEFAULT 0 NOT NULL,
"mission1_19" int4 DEFAULT 0 NOT NULL,
"mission1_20" int4 DEFAULT 0 NOT NULL,
"mission1_21" int4 DEFAULT 0 NOT NULL,
"mission1_22" int4 DEFAULT 0 NOT NULL,
"mission1_23" int4 DEFAULT 0 NOT NULL,
"mission1_24" int4 DEFAULT 0 NOT NULL,
"mission1_25" int4 DEFAULT 0 NOT NULL,
"mission1_26" int4 DEFAULT 0 NOT NULL,
"mission1_27" int4 DEFAULT 0 NOT NULL,
"mission1_28" int4 DEFAULT 0 NOT NULL,
"mission1_29" int4 DEFAULT 0 NOT NULL,
"mission1_30" int4 DEFAULT 0 NOT NULL,
"mission1_31" int4 DEFAULT 0 NOT NULL,
"mission1_32" int4 DEFAULT 0 NOT NULL,
"mission1_33" int4 DEFAULT 0 NOT NULL,
"mission1_34" int4 DEFAULT 0 NOT NULL,
"mission1_35" int4 DEFAULT 0 NOT NULL,
"mission1_36" int4 DEFAULT 0 NOT NULL,
"mission1_37" int4 DEFAULT 0 NOT NULL,
"mission1_38" int4 DEFAULT 0 NOT NULL,
"mission1_39" int4 DEFAULT 0 NOT NULL,
"mission1_40" int4 DEFAULT 0 NOT NULL,
"mission2_1" int4 DEFAULT 0 NOT NULL,
"mission2_2" int4 DEFAULT 0 NOT NULL,
"mission2_3" int4 DEFAULT 0 NOT NULL,
"mission2_4" int4 DEFAULT 0 NOT NULL,
"mission2_5" int4 DEFAULT 0 NOT NULL,
"mission2_6" int4 DEFAULT 0 NOT NULL,
"mission2_7" int4 DEFAULT 0 NOT NULL,
"mission2_8" int4 DEFAULT 0 NOT NULL,
"mission2_9" int4 DEFAULT 0 NOT NULL,
"mission2_10" int4 DEFAULT 0 NOT NULL,
"mission2_11" int4 DEFAULT 0 NOT NULL,
"mission2_12" int4 DEFAULT 0 NOT NULL,
"mission2_13" int4 DEFAULT 0 NOT NULL,
"mission2_14" int4 DEFAULT 0 NOT NULL,
"mission2_15" int4 DEFAULT 0 NOT NULL,
"mission2_16" int4 DEFAULT 0 NOT NULL,
"mission2_17" int4 DEFAULT 0 NOT NULL,
"mission2_18" int4 DEFAULT 0 NOT NULL,
"mission2_19" int4 DEFAULT 0 NOT NULL,
"mission2_20" int4 DEFAULT 0 NOT NULL,
"mission2_21" int4 DEFAULT 0 NOT NULL,
"mission2_22" int4 DEFAULT 0 NOT NULL,
"mission2_23" int4 DEFAULT 0 NOT NULL,
"mission2_24" int4 DEFAULT 0 NOT NULL,
"mission2_25" int4 DEFAULT 0 NOT NULL,
"mission2_26" int4 DEFAULT 0 NOT NULL,
"mission2_27" int4 DEFAULT 0 NOT NULL,
"mission2_28" int4 DEFAULT 0 NOT NULL,
"mission2_29" int4 DEFAULT 0 NOT NULL,
"mission2_30" int4 DEFAULT 0 NOT NULL,
"mission2_31" int4 DEFAULT 0 NOT NULL,
"mission2_32" int4 DEFAULT 0 NOT NULL,
"mission2_33" int4 DEFAULT 0 NOT NULL,
"mission2_34" int4 DEFAULT 0 NOT NULL,
"mission2_35" int4 DEFAULT 0 NOT NULL,
"mission2_36" int4 DEFAULT 0 NOT NULL,
"mission2_37" int4 DEFAULT 0 NOT NULL,
"mission2_38" int4 DEFAULT 0 NOT NULL,
"mission2_39" int4 DEFAULT 0 NOT NULL,
"mission2_40" int4 DEFAULT 0 NOT NULL,
"mission3_1" int4 DEFAULT 0 NOT NULL,
"mission3_2" int4 DEFAULT 0 NOT NULL,
"mission3_3" int4 DEFAULT 0 NOT NULL,
"mission3_4" int4 DEFAULT 0 NOT NULL,
"mission3_5" int4 DEFAULT 0 NOT NULL,
"mission3_6" int4 DEFAULT 0 NOT NULL,
"mission3_7" int4 DEFAULT 0 NOT NULL,
"mission3_8" int4 DEFAULT 0 NOT NULL,
"mission3_9" int4 DEFAULT 0 NOT NULL,
"mission3_10" int4 DEFAULT 0 NOT NULL,
"mission3_11" int4 DEFAULT 0 NOT NULL,
"mission3_12" int4 DEFAULT 0 NOT NULL,
"mission3_13" int4 DEFAULT 0 NOT NULL,
"mission3_14" int4 DEFAULT 0 NOT NULL,
"mission3_15" int4 DEFAULT 0 NOT NULL,
"mission3_16" int4 DEFAULT 0 NOT NULL,
"mission3_17" int4 DEFAULT 0 NOT NULL,
"mission3_18" int4 DEFAULT 0 NOT NULL,
"mission3_19" int4 DEFAULT 0 NOT NULL,
"mission3_20" int4 DEFAULT 0 NOT NULL,
"mission3_21" int4 DEFAULT 0 NOT NULL,
"mission3_22" int4 DEFAULT 0 NOT NULL,
"mission3_23" int4 DEFAULT 0 NOT NULL,
"mission3_24" int4 DEFAULT 0 NOT NULL,
"mission3_25" int4 DEFAULT 0 NOT NULL,
"mission3_26" int4 DEFAULT 0 NOT NULL,
"mission3_27" int4 DEFAULT 0 NOT NULL,
"mission3_28" int4 DEFAULT 0 NOT NULL,
"mission3_29" int4 DEFAULT 0 NOT NULL,
"mission3_30" int4 DEFAULT 0 NOT NULL,
"mission3_31" int4 DEFAULT 0 NOT NULL,
"mission3_32" int4 DEFAULT 0 NOT NULL,
"mission3_33" int4 DEFAULT 0 NOT NULL,
"mission3_34" int4 DEFAULT 0 NOT NULL,
"mission3_35" int4 DEFAULT 0 NOT NULL,
"mission3_36" int4 DEFAULT 0 NOT NULL,
"mission3_37" int4 DEFAULT 0 NOT NULL,
"mission3_38" int4 DEFAULT 0 NOT NULL,
"mission3_39" int4 DEFAULT 0 NOT NULL,
"mission3_40" int4 DEFAULT 0 NOT NULL,
"mission4_1" int4 DEFAULT 0 NOT NULL,
"mission4_2" int4 DEFAULT 0 NOT NULL,
"mission4_3" int4 DEFAULT 0 NOT NULL,
"mission4_4" int4 DEFAULT 0 NOT NULL,
"mission4_5" int4 DEFAULT 0 NOT NULL,
"mission4_6" int4 DEFAULT 0 NOT NULL,
"mission4_7" int4 DEFAULT 0 NOT NULL,
"mission4_8" int4 DEFAULT 0 NOT NULL,
"mission4_9" int4 DEFAULT 0 NOT NULL,
"mission4_10" int4 DEFAULT 0 NOT NULL,
"mission4_11" int4 DEFAULT 0 NOT NULL,
"mission4_12" int4 DEFAULT 0 NOT NULL,
"mission4_13" int4 DEFAULT 0 NOT NULL,
"mission4_14" int4 DEFAULT 0 NOT NULL,
"mission4_15" int4 DEFAULT 0 NOT NULL,
"mission4_16" int4 DEFAULT 0 NOT NULL,
"mission4_17" int4 DEFAULT 0 NOT NULL,
"mission4_18" int4 DEFAULT 0 NOT NULL,
"mission4_19" int4 DEFAULT 0 NOT NULL,
"mission4_20" int4 DEFAULT 0 NOT NULL,
"mission4_21" int4 DEFAULT 0 NOT NULL,
"mission4_22" int4 DEFAULT 0 NOT NULL,
"mission4_23" int4 DEFAULT 0 NOT NULL,
"mission4_24" int4 DEFAULT 0 NOT NULL,
"mission4_25" int4 DEFAULT 0 NOT NULL,
"mission4_26" int4 DEFAULT 0 NOT NULL,
"mission4_27" int4 DEFAULT 0 NOT NULL,
"mission4_28" int4 DEFAULT 0 NOT NULL,
"mission4_29" int4 DEFAULT 0 NOT NULL,
"mission4_30" int4 DEFAULT 0 NOT NULL,
"mission4_31" int4 DEFAULT 0 NOT NULL,
"mission4_32" int4 DEFAULT 0 NOT NULL,
"mission4_33" int4 DEFAULT 0 NOT NULL,
"mission4_34" int4 DEFAULT 0 NOT NULL,
"mission4_35" int4 DEFAULT 0 NOT NULL,
"mission4_36" int4 DEFAULT 0 NOT NULL,
"mission4_37" int4 DEFAULT 0 NOT NULL,
"mission4_38" int4 DEFAULT 0 NOT NULL,
"mission4_39" int4 DEFAULT 0 NOT NULL,
"mission4_40" int4 DEFAULT 0 NOT NULL,
"card1" int4 DEFAULT 0 NOT NULL,
"card2" int4 DEFAULT 0 NOT NULL,
"card3" int4 DEFAULT 0 NOT NULL,
"card4" int4 DEFAULT 0 NOT NULL,
"active" int4 DEFAULT 0 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_missoes
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_mouse
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_mouse";
CREATE TABLE "public"."jogador_mouse" (
"player_id" int8 NOT NULL,
"v_1" int4 DEFAULT 0,
"v_2" int4 DEFAULT 0,
"v_3" int4 DEFAULT 0,
"v_4" int4 DEFAULT 0,
"v_5" int4 DEFAULT 0,
"v_6" int4 DEFAULT 0,
"v_7" int4 DEFAULT 0,
"v_8" int4 DEFAULT 0,
"v_9" int4 DEFAULT 1,
"v_10" int4 DEFAULT 1,
"v_11" int4 DEFAULT 0,
"v_12" int4 DEFAULT 0,
"v_13" int4 DEFAULT 0,
"v_14" int4 DEFAULT 0,
"v_15" int4 DEFAULT 0,
"v_16" int4 DEFAULT 0,
"v_17" int4 DEFAULT 0,
"v_18" int4 DEFAULT 0,
"v_19" int4 DEFAULT 0,
"v_20" int4 DEFAULT 1,
"v_21" int4 DEFAULT 1,
"v_22" int4 DEFAULT 0,
"v_23" int4 DEFAULT 0,
"v_24" int4 DEFAULT 0,
"v_25" int4 DEFAULT 0,
"v_26" int4 DEFAULT 0,
"v_27" int4 DEFAULT 0,
"v_28" int4 DEFAULT 0,
"v_29" int4 DEFAULT 0,
"v_30" int4 DEFAULT 0,
"v_31" int4 DEFAULT 0,
"v_32" int4 DEFAULT 0,
"v_33" int4 DEFAULT 0,
"v_34" int4 DEFAULT 0,
"v_35" int4 DEFAULT 0,
"v_36" int4 DEFAULT 0,
"v_37" int4 DEFAULT 0,
"v_38" int4 DEFAULT 0,
"v_39" int4 DEFAULT 0,
"v_40" int4 DEFAULT 0,
"v_41" int4 DEFAULT 0,
"v_42" int4 DEFAULT 0,
"v_43" int4 DEFAULT 0
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_mouse
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_teclado
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_teclado";
CREATE TABLE "public"."jogador_teclado" (
"player_id" int8 NOT NULL,
"k_esquerda" int4 DEFAULT 10,
"k_direita" int4 DEFAULT 13,
"k_frente" int4 DEFAULT 32,
"k_atras" int4 DEFAULT 28,
"k_pular" int4 DEFAULT 44,
"k_andar" int4 DEFAULT 40,
"k_agachar" int4 DEFAULT 38,
"k_o_atr" int4 DEFAULT 15,
"k_atirar" int4 DEFAULT 1,
"k_scope" int4 DEFAULT 2,
"k_recarregar" int4 DEFAULT 27,
"k_trc_arm" int4 DEFAULT 29,
"k_arm_qq" int4 DEFAULT 26,
"k_arm_ant" int4 DEFAULT 16,
"k_arm_pos" int4 DEFAULT 32,
"k_jog_arm" int4 DEFAULT 16,
"k_placar" int4 DEFAULT 55,
"k_mapa" int4 DEFAULT 22,
"k_mapa_pos" int4 DEFAULT 92,
"k_mapa_ant" int4 DEFAULT 91,
"k_chat" int4 DEFAULT 37,
"k_chat_g" int4 DEFAULT 64,
"k_chat_t" int4 DEFAULT 65,
"k_chat_hz" int4 DEFAULT 21,
"k_chat_v" int4 DEFAULT 31,
"k_chat_c" int4 DEFAULT 66,
"k_rad_t" int4 DEFAULT 35,
"k_rad_p" int4 DEFAULT 33,
"k_rad_i" int4 DEFAULT 12,
"k_bomb_d" int4 DEFAULT 14,
"k_sen_pos" int4 DEFAULT 49,
"k_sen_ant" int4 DEFAULT 50,
"k_print" int4 DEFAULT 70,
"k_mira_x" int4 DEFAULT 11,
"k_gravar" int4 DEFAULT 58,
"k_valor1" int4 DEFAULT 57,
"k_valor2" int4 DEFAULT 248,
"k_valor3" int4 DEFAULT 16,
"k_valor4" int4 DEFAULT 0,
"k_macro_e" int4 DEFAULT 69,
"armas1" int4 DEFAULT 1,
"armas2" int4 DEFAULT 2,
"armas3" int4 DEFAULT 3,
"armas4" int4 DEFAULT 4,
"armas5" int4 DEFAULT 5,
"armas6" int4 DEFAULT 6,
"macro1" varchar COLLATE "default" DEFAULT ''::character varying,
"macro2" varchar COLLATE "default" DEFAULT ''::character varying,
"macro3" varchar COLLATE "default" DEFAULT ''::character varying,
"macro4" varchar COLLATE "default" DEFAULT ''::character varying,
"macro5" varchar COLLATE "default" DEFAULT ''::character varying,
"k_max_value" int8 DEFAULT 4294967295::bigint,
"k_valor5" int4 DEFAULT 232
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_teclado
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_titulos
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_titulos";
CREATE TABLE "public"."jogador_titulos" (
"player_id" int8 NOT NULL,
"slot" int4 NOT NULL,
"equip1" int4 NOT NULL,
"equip2" int4 NOT NULL,
"equip3" int4 NOT NULL,
"title1" int4 NOT NULL,
"title2" int4 NOT NULL,
"title3" int4 NOT NULL,
"title4" int4 NOT NULL,
"title5" int4 NOT NULL,
"title6" int4 NOT NULL,
"title7" int4 NOT NULL,
"title8" int4 NOT NULL,
"title9" int4 NOT NULL,
"title10" int4 NOT NULL,
"title11" int4 NOT NULL,
"title12" int4 NOT NULL,
"title13" int4 NOT NULL,
"title14" int4 NOT NULL,
"title15" int4 NOT NULL,
"title16" int4 NOT NULL,
"title17" int4 NOT NULL,
"title18" int4 NOT NULL,
"title19" int4 NOT NULL,
"title20" int4 NOT NULL,
"title21" int4 NOT NULL,
"title22" int4 NOT NULL,
"title23" int4 NOT NULL,
"title24" int4 NOT NULL,
"title25" int4 NOT NULL,
"title26" int4 NOT NULL,
"title27" int4 NOT NULL,
"title28" int4 NOT NULL,
"title29" int4 NOT NULL,
"title30" int4 NOT NULL,
"title31" int4 NOT NULL,
"title32" int4 NOT NULL,
"title33" int4 NOT NULL,
"title34" int4 NOT NULL,
"title35" int4 NOT NULL,
"title36" int4 NOT NULL,
"title37" int4 NOT NULL,
"title38" int4 NOT NULL,
"title39" int4 NOT NULL,
"title40" int4 NOT NULL,
"title41" int4 NOT NULL,
"title42" int4 NOT NULL,
"title43" int4 NOT NULL,
"title44" int4 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_titulos
-- ----------------------------

-- ----------------------------
-- Table structure for jogador_vip
-- ----------------------------
DROP TABLE IF EXISTS "public"."jogador_vip";
CREATE TABLE "public"."jogador_vip" (
"player_id" int8 NOT NULL,
"pc_cafe" int4 NOT NULL,
"expirate" int4 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of jogador_vip
-- ----------------------------

-- ----------------------------
-- Table structure for loja
-- ----------------------------
DROP TABLE IF EXISTS "public"."loja";
CREATE TABLE "public"."loja" (
"id" int4 DEFAULT nextval('loja_seq'::regclass) NOT NULL,
"item_id" int4 NOT NULL,
"name" varchar(255) COLLATE "default" DEFAULT ''::character varying,
"gold" int4 NOT NULL,
"cash" int4 NOT NULL,
"count" int4 NOT NULL,
"tag" varchar(32) COLLATE "default" DEFAULT ''::character varying NOT NULL,
"type" int4 DEFAULT 0 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of loja
-- ----------------------------
INSERT INTO "public"."loja" VALUES ('4', '100003017', 'AK-47 Silver 1D', '0', '350', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('5', '100003017', 'AK-47 Silver 7D', '0', '1050', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('6', '100003017', 'AK-47 Silver 30D', '0', '3500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('7', '100003019', 'SG-550 Silver 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('8', '100003019', 'SG-550 Silver 7D', '0', '900', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('9', '100003019', 'SG-550 Silver 30D', '0', '3000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('19', '200004021', 'K-1 Silver 1D', '0', '100', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('20', '200004021', 'K-1 Silver 7D', '0', '300', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('21', '200004021', 'K-1 Silver 30D', '0', '1000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('25', '300005004', 'SSG-69 Camoflage 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('26', '300005004', 'SSG-69 Camoflage 7D', '0', '750', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('27', '300005004', 'SSG-69 Camoflage 30D', '0', '2500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('28', '300005009', 'PSG-1 Silver  1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('29', '300005009', 'PSG-1 Silver  7D', '0', '900', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('30', '300005009', 'PSG-1 Silver  30D', '0', '3000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('37', '300005008', 'SSG-69 Silver 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('38', '300005008', 'SSG-69 Silver 7D', '0', '600', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('39', '300005008', 'SSG-69 Silver 30D', '0', '2000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('40', '1001002004', 'Keen Eyes 100U', '20000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('41', '400006008', '870MCS Silver 1D', '0', '150', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('42', '400006008', '870MCS Silver 7D', '0', '450', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('43', '400006008', '870MCS Silver 30D', '0', '1500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('47', '100003038', 'G36C Ext. D 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('48', '100003038', 'G36C Ext. D 7D', '0', '900', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('49', '100003038', 'G36C Ext. D 30D', '0', '3000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('50', '100003040', 'AUG A3 D 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('51', '100003040', 'AUG A3 D 7D', '0', '1000', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('52', '100003040', 'AUG A3 D 30D', '0', '4000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('53', '100003039', 'AK SOPMOD D 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('54', '100003039', 'AK SOPMOD D 7D', '0', '1200', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('55', '100003039', 'AK SOPMOD D 30D', '0', '4000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('56', '200004024', 'MP5K Gold D. 1D', '0', '150', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('57', '200004024', 'MP5K Gold D. 7D', '0', '450', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('58', '200004024', 'MP5K Gold D. 30D', '0', '1500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('59', '200004025', 'Spectre W. D. 1D', '0', '200', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('60', '200004025', 'Spectre W. D. 7D', '0', '600', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('61', '200004025', 'Spectre W. D. 30D', '0', '2000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('62', '200004029', 'P90 Ext. D 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('63', '200004029', 'P90 Ext. D 7D', '0', '750', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('64', '200004029', 'P90 Ext. D 30D', '0', '2500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('65', '300005014', 'Dragunov Gold D. 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('66', '300005014', 'Dragunov Gold D. 7D', '0', '900', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('67', '300005014', 'Dragunov Gold D. 30D', '0', '3000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('71', '300005017', 'L115A1 D. 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('72', '300005017', 'L115A1 D. 7D', '0', '1200', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('73', '300005017', 'L115A1 D. 30D', '0', '4000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('74', '400006011', '870MCS W. D 1D', '0', '200', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('75', '400006011', '870MCS W. D 7D', '0', '600', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('76', '400006011', '870MCS W. D 30D', '0', '2000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('77', '400006012', 'SPAS-15 D 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('78', '400006012', 'SPAS-15 D 7D', '0', '750', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('79', '400006012', 'SPAS-15 D 30D', '0', '2500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('83', '601014005', 'Dual Handgun D. 1D', '0', '150', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('84', '601014005', 'Dual Handgun D. 7D', '0', '450', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('85', '601014005', 'Dual Handgun D. 30D', '0', '1500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('86', '601014006', 'Dual Desert Eagle D 1D', '0', '200', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('87', '601014006', 'Dual Desert Eagle D 7D', '0', '600', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('88', '601014006', 'Dual Desert Eagle D 30D', '0', '2000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('89', '601002012', 'C. Phyton D. 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('90', '601002012', 'C. Phyton D. 7D', '0', '750', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('91', '601002012', 'C. Phyton D. 30D', '0', '2500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('92', '702015002', 'Dual Knife D. 1D', '0', '100', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('93', '702015002', 'Dual Knife D. 7D', '0', '300', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('94', '702015002', 'Dual Knife D. 30D', '0', '1000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('95', '702001011', 'Amok Kukri D. 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('96', '702001011', 'Amok Kukri D. 7D', '0', '750', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('97', '702001011', 'Amok Kukri D. 30D', '0', '2500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('98', '803007006', 'C-5 D. 1D', '0', '100', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('99', '803007006', 'C-5 D. 7D', '0', '300', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('100', '803007006', 'C-5 D. 30D', '0', '1000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('101', '904007007', 'WP Smoke D. 1D', '0', '100', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('102', '904007007', 'WP Smoke D. 7D', '0', '300', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('103', '904007007', 'WP Smoke D. 30D', '0', '1000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('107', '200004039', 'Kriss S.V Black 1D', '0', '350', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('108', '200004039', 'Kriss S.V Black 7D', '0', '1050', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('109', '200004039', 'Kriss S.V Black 30D', '0', '3500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('113', '300005011', 'Dragunov CS. 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('114', '300005011', 'Dragunov CS. 7D', '0', '900', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('115', '300005011', 'Dragunov CS. 30D', '0', '3000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('116', '1102003002', 'Capacete Comum 1D', '0', '150', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('117', '1102003002', 'Capacete Comum 7D', '0', '750', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('118', '1102003002', 'Capacete Comum 30D', '0', '2000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('119', '1104003003', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Camuflagem Tigre Russo 1D', '0', '200', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('120', '1104003003', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Camuflagem Tigre Russo 7D', '0', '850', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('121', '1104003003', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Camuflagem Tigre Russo 30D', '0', '3000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('122', '1104003004', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Camuflagem Naval 1D', '0', '200', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('123', '1104003004', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Camuflagem Naval 7D', '0', '850', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('124', '1104003004', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Camuflagem Naval 30D', '0', '3000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('128', '1300002001', 'Exp 30% 1D', '0', '300', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('129', '1300002007', 'Exp 30% 7D', '0', '1500', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('130', '1300002030', 'Exp 30% 30D', '0', '4500', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('131', '1300004001', 'Gold 30% 1D', '0', '300', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('132', '1300004007', 'Gold 30% 7D', '0', '1500', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('133', '1300004030', 'Gold 30% 30D', '0', '4500', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('134', '1300007001', '30% ReduÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o de respawn 1D', '0', '200', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('135', '1300007007', '30% ReduÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o de respawn 7D', '0', '850', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('136', '1300007030', '30% ReduÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o de respawn 30D', '0', '3000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('137', '1300008001', 'BÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â´nus 40% muniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o 1D', '0', '200', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('138', '1300008007', 'BÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â´nus 40% muniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o 7D', '0', '850', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('139', '1300008030', 'BÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â´nus 40% muniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o 30D', '0', '3000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('140', '1301049000', 'Resetar Kill/Death 1U', '0', '3000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('141', '1301048000', 'Resetar Win/Losers 1U', '0', '3000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('142', '1301047000', 'AlteraÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o de Nick 1U', '0', '5000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('143', '1104003006', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara de Fogo 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('144', '1104003006', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara de Fogo 7D', '0', '1000', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('145', '1104003006', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara de Fogo 30D', '0', '3500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('146', '1104003008', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Hockey 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('147', '1104003008', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Hockey 7D', '0', '1000', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('148', '1104003008', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Hockey 30D', '0', '3500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('152', '1104003009', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara No Alvo 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('153', '1104003009', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara No Alvo 7D', '0', '1000', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('154', '1104003009', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara No Alvo 30D', '0', '3500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('155', '1104003007', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Duas Cores 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('156', '1104003007', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Duas Cores 7D', '0', '1000', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('157', '1104003007', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Duas Cores 30D', '0', '3500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('158', '1001001011', 'Reinforced D-Fox 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('159', '1001001011', 'Reinforced D-Fox 7D', '0', '1600', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('160', '1001001011', 'Reinforced D-Fox 30D', '0', '8000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('161', '1001002012', 'Reinforced Leopard 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('162', '1001002012', 'Reinforced Leopard 7D', '0', '1600', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('163', '1001002012', 'Reinforced Leopard 30D', '0', '8000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('164', '1102003007', 'Capacete AvanÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ado Plus 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('165', '1102003007', 'Capacete AvanÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ado Plus 7D', '0', '1500', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('166', '1102003007', 'Capacete AvanÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ado Plus 30D', '0', '4500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('167', '1102003006', 'Capacete Rastreador 1D', '0', '200', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('168', '1102003006', 'Capacete Rastreador 7D', '0', '850', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('169', '1102003006', 'Capacete Rastreador 30D', '0', '3000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('170', '1301056000', 'Resetar Pontos Clan 1U', '0', '5000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('171', '1301053000', 'Resetar Clan Win/Losers 1U', '0', '5000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('172', '1301052000', 'Alterar Emblema do ClÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£ 1U', '0', '3000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('173', '1300032001', 'MuniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o Hollow Point 1D', '0', '250', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('174', '1300032007', 'MuniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o Hollow Point 7D', '0', '1000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('175', '1300032030', 'MuniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o Hollow Point 30D', '0', '3600', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('176', '1300031001', 'Bala de Ferro 1D', '0', '250', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('177', '1300031007', 'Bala de Ferro 7D', '0', '1000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('178', '1300031030', 'Bala de Ferro 30D', '0', '3600', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('179', '1300030001', 'Colete ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â  prova de balas (5%) 1D', '0', '300', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('180', '1300030007', 'Colete ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â  prova de balas (5%) 7D', '0', '1200', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('181', '1300030030', 'Colete ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â  prova de balas (5%) 30D', '0', '4000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('182', '1301055000', 'Aumento de membros ClÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£', '0', '3000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('183', '1300035001', 'Explosivo Extra 1D', '0', '250', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('184', '1300035007', 'Explosivo Extra 7D', '0', '1200', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('185', '1300035030', 'Explosivo Extra 30D', '0', '4000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('186', '1300064001', '50% ReduÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o de respawn 1D', '0', '250', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('187', '1300064007', '50% ReduÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o de respawn 7D', '0', '1000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('188', '1300064030', '50% ReduÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o de respawn 30D', '0', '3500', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('189', '1300040001', 'Life extra 5% 1D', '0', '250', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('190', '1300040007', 'Life extra 5% 7D', '0', '770', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('191', '1300040030', 'Life extra 5% 30D', '0', '2700', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('192', '702001014', 'Machete de Combate 1D', '0', '200', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('193', '702001014', 'Machete de Combate 7D', '0', '600', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('194', '702001014', 'Machete de Combate 30D', '0', '2000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('195', '1300036001', 'MuniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o Hollow Point Fortificada 1D', '0', '270', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('196', '1300036007', 'MuniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o Hollow Point Fortificada 7D', '0', '1300', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('197', '1300036030', 'MuniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o Hollow Point Fortificada 30D', '0', '4000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('198', '1300017001', 'Receber Drop 1D', '0', '350', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('199', '1300017007', 'Receber Drop 7D', '0', '1500', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('200', '1300017030', 'Receber Drop 30D', '0', '4800', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('201', '1001002014', 'Reinforced Hide 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('202', '1001002014', 'Reinforced Hide 7D', '0', '2000', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('203', '1001002014', 'Reinforced Hide 30D', '0', '8000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('204', '1001001013', 'Reinforced Viper Red 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('205', '1001001013', 'Reinforced Viper Red 7D', '0', '2000', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('206', '1001001013', 'Reinforced Viper Red 30D', '0', '8000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('207', '400006004', '870MCS W. 100U', '7500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('209', '100003015', 'AK SOPMOD 100U', '22500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('211', '100003002', 'AK-47 Ext. 100U', '15000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('213', '702001004', 'Amok Kukri 100U', '9000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('215', '100003036', 'AUG A3 100U', '17250', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('217', '803007004', 'C-5 100U', '4500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('219', '601002007', 'C. Phyton 100U', '10500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('221', '601002001', 'Desert Eagle 100U', '4500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('223', '300005001', 'Dragunov 100U', '11250', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('225', '300005006', 'Dragunov Gold 100U', '13500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('227', '601014002', 'Dual Desert Eagle 100U', '7500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('229', '601014001', 'Dual Handgun 100U', '6000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('231', '803007008', 'K-413 100U', '3750', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('233', '702015001', 'Dual Knife 100U', '4500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('235', '100003005', 'F2000 Ext. 100U', '11250', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('237', '904007003', 'FlashBang 100U', '2250', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('239', '100003013', 'G36C Ext. 100U', '16500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('241', '300005005', 'L115A1 100U', '22500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('243', '500010001', 'MK 46 Ext. 100U', '11250', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('245', '601002002', 'MK.23 Ext 100U', '2250', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('247', '200004001', 'MP5K Ext. 100U', '4500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('249', '200004007', 'MP5K Gold. 100U', '6000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('251', '200004004', 'MP7 Ext. 100U', '7500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('253', '200004011', 'P90 Ext. 100U', '9000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('255', '601013001', 'P99 & HAK 100U', '6000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('257', '300005002', 'PSG-1 100U', '15000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('259', '200004043', 'SS1-R5 Carabine 100U', '9900', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('263', '100003014', 'SG-550 Camoflage 100U', '18750', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('265', '400006003', 'SPAS-15 100U', '13500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('269', '200004009', 'Spectre W. 100U', '7500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('271', '100003001', 'SG-550 Ext. 100U', '12750', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('273', '1001001003', 'Tarantula 100U', '20000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('274', '200004008', 'UMP45 Ext. 100U', '4500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('276', '904007005', 'WP Smoke 100U', '4500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('281', '100003023', 'M4A1 Gold. 1D', '0', '270', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('282', '100003023', 'M4A1 Gold. 7D', '0', '810', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('283', '100003023', 'M4A1 Gold. 30D', '0', '2700', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('290', '601014004', 'Dual Desert Eagle G. 1D', '0', '230', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('291', '601014004', 'Dual Desert Eagle G. 7D', '0', '690', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('292', '601014004', 'Dual Desert Eagle G. 30D', '0', '2300', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('293', '1105003004', 'Cowboy Hat 100U', '16000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('296', '1300003001', 'Exp 50% 1D', '0', '400', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('297', '1300003007', 'Exp 50% 7D', '0', '1700', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('298', '1300003030', 'Exp 50% 30D', '0', '5000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('299', '200004013', 'Kriss S.V 100U', '9000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('301', '100003048', 'AUG A3 Black 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('302', '100003048', 'AUG A3 Black 7D', '0', '750', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('303', '100003048', 'AUG A3 Black 30D', '0', '2500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('304', '702015003', 'Faca de Osso 1D', '0', '130', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('305', '702015003', 'Faca de Osso 7D', '0', '390', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('306', '702015003', 'Faca de Osso 30D', '0', '1300', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('307', '400006006', 'SPAS-15 Silver 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('308', '400006006', 'SPAS-15 Silver 7D', '0', '900', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('309', '400006006', 'SPAS-15 Silver 30D', '0', '3000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('313', '702001017', 'Fang Blade 1D', '0', '280', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('314', '702001017', 'Fang Blade 7D', '0', '840', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('315', '702001017', 'Fang Blade 30D', '0', '2800', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('316', '100003041', 'AK SOPMOD CG 1D', '0', '450', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('317', '100003041', 'AK SOPMOD CG 7D', '0', '1350', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('318', '100003041', 'AK SOPMOD CG 30D', '0', '4000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('319', '300005031', 'Winchester M70 1D', '0', '350', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('320', '300005031', 'Winchester M70 7D', '0', '1050', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('321', '300005031', 'Winchester M70 30D', '0', '3500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('322', '100003045', 'M4 SR-16 F.C 1D', '0', '420', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('323', '100003045', 'M4 SR-16 F.C 7D', '0', '1260', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('324', '100003045', 'M4 SR-16 F.C 30D', '0', '4200', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('325', '300005020', 'M4 SPR Lvl-3 1D', '0', '380', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('326', '300005020', 'M4 SPR Lvl-3 7D', '0', '1140', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('327', '300005020', 'M4 SPR Lvl-3 30D', '0', '3800', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('328', '1104003015', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara AlienÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â­gina Azul 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('329', '1104003015', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara AlienÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â­gina Azul 7D', '0', '1500', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('330', '1104003015', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara AlienÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â­gina Azul 30D', '0', '4500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('331', '1104003016', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara AlienÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â­gina Vermelho 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('332', '1104003016', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara AlienÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â­gina Vermelho 7D', '0', '1500', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('333', '1104003016', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara AlienÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â­gina Vermelho 30D', '0', '4500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('337', '1104003014', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara PalhaÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§o Assassino 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('338', '1104003014', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara PalhaÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§o Assassino 7D', '0', '1700', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('339', '1104003014', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara PalhaÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§o Assassino 30D', '0', '5000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('340', '400006017', 'M1887 D 1D', '0', '350', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('341', '400006017', 'M1887 D 7D', '0', '1100', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('342', '400006017', 'M1887 D 30D', '0', '3500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('343', '400006005', 'M1887 100U', '15000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('345', '904007010', 'Smoke Plus 1D', '0', '120', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('346', '904007010', 'Smoke Plus 7D', '0', '360', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('347', '904007010', 'Smoke Plus 30D', '0', '1200', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('348', '1300119001', 'Gold 50% 1D', '0', '400', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('349', '1300119007', 'Gold 50% 7D', '0', '1700', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('350', '1300119030', 'Gold 50% 30D', '0', '5000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('351', '1104003019', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara de Panda 1D', '0', '280', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('352', '1104003019', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara de Panda 7D', '0', '1100', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('353', '1104003019', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara de Panda 30D', '0', '3800', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('354', '300005024', 'PSG-1 Gold 100U', '19500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('357', '500010002', 'MK 46 Silver 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('358', '500010002', 'MK 46 Silver 7D', '0', '750', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('359', '500010002', 'MK 46 Silver 30D', '0', '2500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('363', '1001002018', 'Reinforced Combo Hide (+30% Point) 1D', '0', '420', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('364', '1001002018', 'Reinforced Combo Hide (+30% Point) 7D', '0', '2500', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('365', '1001002018', 'Reinforced Combo Hide (+30% Point) 30D', '0', '9000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('366', '1001002016', 'Reinforced Combo Leopard (+20% EXP) 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('367', '1001002016', 'Reinforced Combo Leopard (+20% EXP) 7D', '0', '2250', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('368', '1001002016', 'Reinforced Combo Leopard (+20% EXP) 30D', '0', '8500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('369', '1001001015', 'Reinforced Combo D-Fox (+20% EXP) 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('370', '1001001015', 'Reinforced Combo D-Fox (+20% EXP) 7D', '0', '2250', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('371', '1001001015', 'Reinforced Combo D-Fox (+20% EXP) 30D', '0', '8500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('372', '1001001017', 'Reinforced Combo Viper Red (+30% Point) 1D', '0', '420', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('373', '1001001017', 'Reinforced Combo Viper Red (+30% Point) 7D', '0', '2500', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('374', '1001001017', 'Reinforced Combo Viper Red (+30% Point) 30D', '0', '9000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('375', '601002018', 'C. Python G. D 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('376', '601002018', 'C. Python G. D 7D', '0', '900', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('377', '601002018', 'C. Python G. D 30D', '0', '3000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('378', '601002017', 'C. Python G 100U', '12000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('380', '300005021', 'Rangemaster .338 1D', '0', '420', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('381', '300005021', 'Rangemaster .338 7D', '0', '1260', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('382', '300005021', 'Rangemaster .338 30D', '0', '4000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('383', '300005022', 'Rangemaster 7.62 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('384', '300005022', 'Rangemaster 7.62 7D', '0', '900', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('385', '300005022', 'Rangemaster 7.62 30D', '0', '3000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('386', '300005023', 'Rangemaster 7.62 STBY 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('387', '300005023', 'Rangemaster 7.62 STBY 7D', '0', '750', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('388', '300005023', 'Rangemaster 7.62 STBY 30D', '0', '2500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('389', '400006020', 'Keltec KSG-15 1D', '0', '450', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('390', '400006020', 'Keltec KSG-15 7D', '0', '1400', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('391', '400006020', 'Keltec KSG-15 30D', '0', '4500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('392', '1102003003', 'Capacete AvanÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ado 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('393', '1102003003', 'Capacete AvanÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ado 7D', '0', '1000', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('394', '1102003003', 'Capacete AvanÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ado 30D', '0', '3500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('395', '1300044001', 'Colete ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â  prova de balas (10%) 1D', '0', '400', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('396', '1300044007', 'Colete ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â  prova de balas (10%) 7D', '0', '1600', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('397', '1300044030', 'Colete ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â  prova de balas (10%) 30D', '0', '5000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('398', '200004022', 'MP7 Silver 1D', '0', '200', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('399', '200004022', 'MP7 Silver 7D', '0', '600', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('400', '200004022', 'MP7 Silver 30D', '0', '2000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('404', '601002022', 'Colt 45 1D', '0', '180', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('405', '601002022', 'Colt 45 7D', '0', '590', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('406', '601002022', 'Colt 45 30D', '0', '1900', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('407', '100003037', 'AUG A3 Gold 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('408', '100003037', 'AUG A3 Gold 7D', '0', '1200', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('409', '100003037', 'AUG A3 Gold 30D', '0', '4000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('410', '200004026', 'Kriss S.V Gold 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('411', '200004026', 'Kriss S.V Gold 7D', '0', '1200', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('412', '200004026', 'Kriss S.V Gold 30D', '0', '4000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('413', '100003053', 'SS2-V4 Para Sniper 100U', '9900', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('415', '1301240000', 'Caixa Cupido', '0', '4000', '3', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('416', '702001066', 'Foice da Morte', '0', '2600', '2592000', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('417', '1302016000', 'Caixa Woody B', '0', '4000', '3', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('418', '1302014000', 'Caixa Woody A', '0', '4000', '3', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('419', '1301854000', 'Caixa Camo Soldier', '0', '4000', '3', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('420', '1301300000', 'Caixa Dolphin', '0', '4000', '3', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('427', '1301202000', 'Caixa Silence', '0', '4000', '3', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('428', '1301298000', 'Caixa Alien', '0', '4000', '3', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('429', '1301901000', 'Caixa Horror', '0', '4000', '3', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('430', '601002011', 'Glock 18 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('431', '601002011', 'Glock 18 7D', '0', '1000', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('432', '601002011', 'Glock 18 30D', '0', '3500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('439', '400006018', 'SPAS-15 MSC 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('440', '400006018', 'SPAS-15 MSC 7D', '0', '1200', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('441', '400006018', 'SPAS-15 MSC 30D', '0', '4000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('442', '1104003013', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara de CrÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¢nio 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('443', '1104003013', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara de CrÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¢nio 7D', '0', '1700', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('444', '1104003013', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara de CrÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¢nio 30D', '0', '5000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('445', '1001001034', 'Rica 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('446', '1001001034', 'Rica 7D', '0', '1600', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('447', '1001001034', 'Rica 30D', '0', '6000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('448', '1001002033', 'Chou 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('449', '1001002033', 'Chou 7D', '0', '1600', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('450', '1001002033', 'Chou 30D', '0', '6000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('451', '601002005', 'Desert Eagle Silver 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('452', '601002005', 'Desert Eagle Silver 7D', '0', '750', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('453', '601002005', 'Desert Eagle Silver 30D', '0', '2500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('454', '1104003012', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Golden Face 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('455', '1104003012', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Golden Face 7D', '0', '1700', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('456', '1104003012', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Golden Face 30D', '0', '5000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('457', '1006003045', 'Mars Dino (Reinforced Sting) 1D', '0', '350', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('458', '1006003045', 'Mars Dino (Reinforced Sting) 7D', '0', '1400', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('459', '1006003045', 'Mars Dino (Reinforced Sting) 30D', '0', '4800', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('463', '1006003046', 'Vulcan Dino (Reinforced Acid) 1D', '0', '350', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('464', '1006003046', 'Vulcan Dino (Reinforced Acid) 7D', '0', '1400', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('465', '1006003046', 'Vulcan Dino (Reinforced Acid) 30D', '0', '4800', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('466', '200004034', 'M4 CQB-R lvl-1 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('467', '200004034', 'M4 CQB-R lvl-1 7D', '0', '750', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('468', '200004034', 'M4 CQB-R lvl-1 30D', '0', '2500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('472', '300005018', 'M4 SPR Lvl-1 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('473', '300005018', 'M4 SPR Lvl-1 7D', '0', '750', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('474', '300005018', 'M4 SPR Lvl-1 30D', '0', '2500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('478', '100003042', 'M4 SR-16 lvl-1 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('479', '100003042', 'M4 SR-16 lvl-1 7D', '0', '720', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('480', '100003042', 'M4 SR-16 lvl-1 30D', '0', '2500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('484', '200018011', 'Dual Micro Uzi Silencer Silver 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('485', '200018011', 'Dual Micro Uzi Silencer Silver 7D', '0', '750', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('486', '200018011', 'Dual Micro Uzi Silencer Silver 30D', '0', '2500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('487', '1103003014', 'Boina Skull', '0', '6000', '2592000', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('488', '1103003006', 'Boina Negra', '0', '6000', '2592000', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('489', '1301853000', 'Caixa Personagem B', '0', '6000', '3', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('490', '1301852000', 'Caixa Personagem A', '0', '6000', '3', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('491', '100003093', 'AUG A3 Blaze', '0', '4200', '2592000', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('492', '1301794000', 'Caixa PBIC2015', '0', '5000', '3', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('493', '1301051000', 'Alterar nome do ClÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£ 1U', '0', '6000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('494', '1300027001', 'Recarregamento rÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡pido 1D', '0', '300', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('495', '1300027007', 'Recarregamento rÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡pido 7D', '0', '1000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('496', '1300027030', 'Recarregamento rÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡pido 30D', '0', '3600', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('497', '1300026001', 'Troca rÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡pida de arma 1D', '0', '300', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('498', '1300026007', 'Troca rÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡pida de arma 7D', '0', '1000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('499', '1300026030', 'Troca rÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡pida de arma 30D', '0', '3600', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('500', '1300037001', 'Exp 100% 1D', '0', '450', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('501', '1300037007', 'Exp 100% 7D', '0', '2500', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('502', '1300037030', 'Exp 100% 30D', '0', '8500', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('503', '1300038001', 'Gold 100% 1D', '0', '450', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('504', '1300038007', 'Gold 100% 7D', '0', '2500', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('505', '1300038030', 'Gold 100% 30D', '0', '8500', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('506', '1300080001', '100% ReduÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o de respawn 1D', '0', '300', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('507', '1300080007', '100% ReduÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o de respawn 7D', '0', '1100', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('508', '1300080030', '100% ReduÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o de respawn 30D', '0', '3800', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('512', '1300011001', 'O bom perdedor 1D', '0', '500', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('513', '1300011007', 'O bom perdedor 7D', '0', '3000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('514', '1300011030', 'O bom perdedor 30D', '0', '9000', '1', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('518', '1301335000', 'Caixa Supreme', '0', '4000', '3', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('520', '1301306000', 'Caixa NewBorn', '0', '4000', '3', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('522', '904007013', 'FlashBang Plus 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('523', '904007013', 'FlashBang Plus 7D', '0', '360', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('524', '904007013', 'FlashBang Plus 30D', '0', '1200', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('525', '300005036', 'L115A1 SE 1D', '0', '460', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('526', '300005036', 'L115A1 SE 7D', '0', '1380', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('527', '300005036', 'L115A1 SE 30D', '0', '4600', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('528', '100003102', 'HK-417 100U', '14300', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('531', '300005035', 'SVU 100U', '19000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('533', '702001009', 'M-7 Gold 1D', '0', '150', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('534', '702001009', 'M-7 Gold 7D', '0', '450', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('535', '702001009', 'M-7 Gold 30D', '0', '1500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('536', '200004010', 'P90 M.C 100U', '9500', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('538', '300005030', 'Cheytac M200 100U', '30000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('541', '100003117', 'SG-550 Reload 100U', '18000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('543', '200004219', 'P90 M.C Gold 1D', '0', '320', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('544', '200004219', 'P90 M.C Gold 7D', '0', '960', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('545', '200004219', 'P90 M.C Gold 30D', '0', '3200', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('549', '702001041', 'Arabian Sword 1D', '0', '280', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('550', '702001041', 'Arabian Sword 7D', '0', '840', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('551', '702001041', 'Arabian Sword 30D', '0', '2800', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('552', '803007018', 'K-413 G 1D', '0', '100', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('553', '803007018', 'K-413 G 7D', '0', '300', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('554', '803007018', 'K-413 G 30D', '0', '1000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('558', '100003116', 'F2000 Reload 100U', '18000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('566', '200004075', 'P90 Ext. Gold 1D', '0', '350', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('567', '200004075', 'P90 Ext. Gold 7D', '0', '1050', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('568', '200004075', 'P90 Ext. Gold 30D', '0', '3500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('574', '200004100', 'MP5K Reload 100U', '8550', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('578', '100003049', 'FAMAS G2 1D', '0', '380', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('579', '100003049', 'FAMAS G2 7D', '0', '1140', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('580', '100003049', 'FAMAS G2 30D', '0', '3800', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('581', '100003050', 'FAMAS G2 Commando 1D', '0', '390', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('582', '100003050', 'FAMAS G2 Commando 7D', '0', '1170', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('583', '100003050', 'FAMAS G2 Commando 30D', '0', '3900', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('584', '100003051', 'FAMAS G2 M203 1D', '0', '390', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('585', '100003051', 'FAMAS G2 M203 7D', '0', '1170', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('586', '100003051', 'FAMAS G2 M203 30D', '0', '3900', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('587', '100003052', 'FAMAS G2 Sniper 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('588', '100003052', 'FAMAS G2 Sniper 7D', '0', '1200', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('589', '100003052', 'FAMAS G2 Sniper 30D', '0', '4000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('590', '100003064', 'FAMAS G2 Commando Gold 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('591', '100003064', 'FAMAS G2 Commando Gold 7D', '0', '1200', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('592', '100003064', 'FAMAS G2 Commando Gold 30D', '0', '4000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('593', '200004107', 'MP9 Ext 100U', '8250', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('598', '601002035', 'MK.23 Reload 100U', '4800', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('600', '601013004', 'P99 & HAK Sl. D 100U', '6400', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('608', '100003094', 'SCAR-L Carbine 1D', '0', '280', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('609', '100003094', 'SCAR-L Carbine 7D', '0', '840', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('610', '100003094', 'SCAR-L Carbine 30D', '0', '2800', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('611', '100003096', 'SCAR-L FC 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('612', '100003096', 'SCAR-L FC 7D', '0', '1200', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('613', '100003096', 'SCAR-L FC 30D', '0', '4000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('614', '100003095', 'SCAR-L Recon 1D', '0', '290', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('615', '100003095', 'SCAR-L Recon 7D', '0', '870', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('616', '100003095', 'SCAR-L Recon 30D', '0', '2900', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('618', '200004106', 'PP-19 Bizon 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('619', '200004106', 'PP-19 Bizon 7D', '0', '900', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('620', '200004106', 'PP-19 Bizon 30D', '0', '3000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('621', '601002036', 'Desert Eagle Reload 100U', '5400', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('623', '100003123', 'TAR-21 1D', '0', '280', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('624', '100003123', 'TAR-21 7D', '0', '840', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('625', '100003123', 'TAR-21 30D', '0', '2800', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('626', '300005059', 'PSG-1 Reload 100U', '18750', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('630', '300005072', 'Walther WA2000 100U', '21600', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('632', '100003143', 'FG-42 100U', '11780', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('635', '904007061', 'Yellow Smoke', '0', '1500', '2592000', 'HOT', '1');
INSERT INTO "public"."loja" VALUES ('637', '1104003021', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara da Morte 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('638', '1104003021', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara da Morte 7D', '0', '1200', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('639', '1104003021', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara da Morte 30D', '0', '3800', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('640', '300005034', 'DSR-1 100U', '24000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('643', '601002015', 'RB 454 SS8M 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('644', '601002015', 'RB 454 SS8M 7D', '0', '900', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('645', '601002015', 'RB 454 SS8M 30D', '0', '3000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('646', '601002013', 'RB 454 SS2M 1D', '0', '220', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('647', '601002013', 'RB 454 SS2M 7D', '0', '660', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('648', '601002013', 'RB 454 SS2M 30D', '0', '2200', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('649', '601002014', 'RB 454 SS5M 1D', '0', '250', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('650', '601002014', 'RB 454 SS5M 7D', '0', '750', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('651', '601002014', 'RB 454 SS5M 30D', '0', '2500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('652', '702023002', 'Black Knuckles 1D', '0', '120', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('653', '702023002', 'Black Knuckles 7D', '0', '360', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('654', '702023002', 'Black Knuckles 30D', '0', '1200', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('655', '702023004', 'Silver Knuckles 1D', '0', '130', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('656', '702023004', 'Silver Knuckles 7D', '0', '390', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('657', '702023004', 'Silver Knuckles 30D', '0', '1300', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('658', '200004134', 'OA-93 100U', '23000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('661', '1001002053', 'World Hide 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('662', '1001002053', 'World Hide 7D', '0', '2000', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('663', '1001002053', 'World Hide 30D', '0', '8000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('664', '1001001054', 'World Tarantula 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('665', '1001001054', 'World Tarantula 7D', '0', '2000', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('666', '1001001054', 'World Tarantula 30D', '0', '8000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('676', '702001007', 'Mini Axe 100U', '6000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('678', '702001018', 'Ballistic Knife 1D', '0', '300', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('679', '702001018', 'Ballistic Knife 7D', '0', '1080', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('680', '702001018', 'Ballistic Knife 30D', '0', '4000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('681', '200004036', 'M4 CQB-R lvl-3 1D', '0', '350', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('682', '200004036', 'M4 CQB-R lvl-3 7D', '0', '1050', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('683', '200004036', 'M4 CQB-R lvl-3 30D', '0', '3500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('684', '702001012', 'Mini Axe D. 1D', '0', '150', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('685', '702001012', 'Mini Axe D. 7D', '0', '450', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('686', '702001012', 'Mini Axe D. 30D', '0', '1500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('696', '200004136', 'OA-93 Gold 1D', '0', '400', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('697', '200004136', 'OA-93 Gold 7D', '0', '1200', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('698', '200004136', 'OA-93 Gold 30D', '0', '4000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('705', '702001023', 'Keris S. 1D', '0', '230', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('706', '702001023', 'Keris S. 7D', '0', '690', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('707', '702001023', 'Keris S. 30D', '0', '2300', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('708', '100003174', 'XM8 1D', '0', '280', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('709', '100003174', 'XM8 7D', '0', '840', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('710', '100003174', 'XM8 30D', '0', '2800', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('717', '1001002051', 'Hide Kopassus 1D', '0', '420', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('718', '1001002051', 'Hide Kopassus 7D', '0', '2500', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('719', '1001002051', 'Hide Kopassus 30D', '0', '10000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('720', '1001002067', 'Hide Strike 1D', '0', '420', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('721', '1001002067', 'Hide Strike 7D', '0', '2500', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('722', '1001002067', 'Hide Strike 30D', '0', '10000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('723', '1001001068', 'Viper Kopassus 1D', '0', '420', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('724', '1001001068', 'Viper Kopassus 7D', '0', '2500', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('725', '1001001068', 'Viper Kopassus 30D', '0', '10000', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('732', '200004328', 'OA-93 Hallowen 2016', '0', '4000', '2592000', 'HOT', '1');
INSERT INTO "public"."loja" VALUES ('733', '100003300', 'AUG A3 Hallowen 2016', '0', '5000', '2592000', 'HOT', '1');
INSERT INTO "public"."loja" VALUES ('734', '400006062', 'M1887 Obsidian', '0', '2000', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('747', '100003235', 'AUG A3 Obsidian', '0', '4000', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('748', '200004242', 'Kriss S.V Obsidian', '0', '3000', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('749', '300005135', 'Cheytac M200 Obsidian', '0', '3000', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('758', '300005121', 'Cheytac M200 Gold 1D', '0', '530', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('759', '300005121', 'Cheytac M200 Gold 7D', '0', '1590', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('760', '300005121', 'Cheytac M200 Gold 30D', '0', '5300', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('761', '400006059', 'M1887 Gold 1D', '0', '450', '86400', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('762', '400006059', 'M1887 Gold 7D', '0', '1350', '604800', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('763', '400006059', 'M1887 Gold 30D', '0', '4500', '2592000', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('764', '200004341', 'P90 LATIN6', '0', '2400', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('765', '300005189', 'Cheytac M200 Hallowen 2016', '0', '3400', '2592000', 'HOT', '1');
INSERT INTO "public"."loja" VALUES ('766', '1301326000', 'Caixa DarkSteel', '0', '5000', '3', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('770', '300005193', 'Cheytac M200 LATIN6', '0', '3000', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('771', '601002105', 'Colt Python LATIN6', '0', '1250', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('772', '702001166', 'Combat Machete LATIN6', '0', '1200', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('797', '100003312', 'AUG A3 LATIN6', '0', '4000', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('802', '200004339', 'Kriss S.V LATIN6', '0', '3000', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('804', '1103003023', 'PBNC 2016 Beret', '0', '6000', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('808', '1301335000', 'Caixa Supreme 3U', '0', '3000', '3', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('809', '100003335', 'AUG A3 GSL 2017', '0', '4000', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('810', '200004381', 'Kriss S.V GSL 2017', '0', '3000', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('811', '200004383', 'OA-93 GSL 2017', '0', '3200', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('812', '400006096', 'M1887 GSL 2017', '0', '2400', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('813', '300005209', 'AS-50 GSL 2017', '0', '3000', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('814', '300005032', 'Barrett M82A1 100U', '50000', '0', '100', 'NOT', '1');
INSERT INTO "public"."loja" VALUES ('815', '200004389', 'Kriss S.V Chicano', '0', '3000', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('817', '100003341', 'AUG A3 Chicano', '0', '4000', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('818', '300005212', 'Cheytac M200 Chicano', '0', '3000', '2592000', 'VIP_PLUS', '1');
INSERT INTO "public"."loja" VALUES ('819', '1301299000', 'Caixa Blue Diamond 3U', '0', '3000', '3', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('820', '100003364', 'AUG A3 Kareem', '0', '5000', '2592000', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('821', '200004431', 'OA-93 Kareem', '0', '4000', '2592000', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('822', '200004433', 'P90 M.C Kareem', '0', '3200', '2592000', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('823', '300005230', 'Cheytac M200 Kareem', '0', '4000', '2592000', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('824', '601002122', 'Colt Python Kareem', '0', '3000', '2592000', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('825', '702001193', 'Karambit Kareem', '0', '2400', '2592000', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('826', '702001194', 'Fang Blade Kareem', '0', '2000', '2592000', 'NEW', '1');
INSERT INTO "public"."loja" VALUES ('827', '904007059', 'Blue Smoke', '0', '1000', '2592000', 'VIP_PLUS', '1');

-- ----------------------------
-- Table structure for loja_gifts
-- ----------------------------
DROP TABLE IF EXISTS "public"."loja_gifts";
CREATE TABLE "public"."loja_gifts" (
"id" int4 NOT NULL,
"item_id" int4 NOT NULL,
"name" varchar COLLATE "default",
"enable" bool NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of loja_gifts
-- ----------------------------

-- ----------------------------
-- Table structure for noticias
-- ----------------------------
DROP TABLE IF EXISTS "public"."noticias";
CREATE TABLE "public"."noticias" (
"id" int4,
"titulo" varchar(255) COLLATE "default",
"data" varchar(255) COLLATE "default",
"tipo" varchar(255) COLLATE "default",
"postagem" varchar(255) COLLATE "default",
"autor" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of noticias
-- ----------------------------
INSERT INTO "public"."noticias" VALUES ('2', 'Resumo da AtualizaÃ§Ã£o Download Obrigatorio', '2018-03-21', 'Atualizacao', '<p><a href="http://www.mediafire.com/file/f31vvllvxwhi1w3/Att.rar">http://www.mediafire.com/file/f31vvllvxwhi1w3/Att.rar</a>&nbsp;</p>
<p>colcoar na pasta GUI em Image pode substituir os arquivos</p>', 'GM_Stonex');
INSERT INTO "public"."noticias" VALUES ('3', 'Fim da AtualizaÃ§Ã£o', '2018-03-21', 'Atualizacao', '<p><a href="http://www.mediafire.com/file/28vlg0gq27j0tl4/tradu%C3%A7%C3%A3o+project-X+v2.rar">http://www.mediafire.com/file/28vlg0gq27j0tl4/tradu%C3%A7%C3%A3o+project-X+v2.rar</a></p>
<p>colocar na pasta pack</p>', 'GM_Stonex');
INSERT INTO "public"."noticias" VALUES ('5', 'Evento de XP 200%', '2018-03-22', 'Eventos', '<p>evento valido ate dia 24/03/2018</p>', 'GM_Stonex');
INSERT INTO "public"."noticias" VALUES ('6', 'Arsenal Novo Disponivel para Vip', '2018-03-22', 'Noticias', '<p><img src="https://i.imgur.com/rZFDKwP.jpg" alt="" width="565" height="238" /></p>', 'GM_Stonex');
INSERT INTO "public"."noticias" VALUES ('8', 'Novo BC Log', '2018-03-23', 'Atualizacao', '<p><a href="http://www.mediafire.com/file/ufdnwai141xcr5s/BCLX.exe">http://www.mediafire.com/file/ufdnwai141xcr5s/BCLX.exe</a></p>', 'GM_Stonex');
INSERT INTO "public"."noticias" VALUES ('9', 'Novo Launcher Obrigatorio', '28-03-2018', 'Noticias', '<p><a href="http://www.mediafire.com/file/y5vji1o59ttx3ei/Launcher.rar">http://www.mediafire.com/file/y5vji1o59ttx3ei/Launcher.rar</a>&nbsp;quando extrair abra a pasta do launcher selecione tudo e copie para pasta da cliente project x e substitua</p>', 'GM_Stonex');

-- ----------------------------
-- Table structure for storage
-- ----------------------------
DROP TABLE IF EXISTS "public"."storage";
CREATE TABLE "public"."storage" (
"id" int4 DEFAULT nextval('storage_seq'::regclass) NOT NULL,
"item_id" int4 NOT NULL,
"name" varchar(255) COLLATE "default" DEFAULT ''::character varying NOT NULL,
"type" varchar(255) COLLATE "default" DEFAULT ''::character varying NOT NULL,
"titulo" int4 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of storage
-- ----------------------------
INSERT INTO "public"."storage" VALUES ('2', '100003214', 'AUG A3 PBNC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('2', '1200065000', 'Colete 90%', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('3', '100003216', 'AUG A3 Mech', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('3', '200004205', 'Kriss S.V PBNC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('4', '100003243', 'AUG A3 Monkey', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('4', '300005114', 'Cheytac M200 PBNC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('5', '100003241', 'AUG A3 XMAS2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('5', '400006053', 'M1887 PBNC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('6', '100003142', 'AUG A3 1st Anniversary', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('6', '400006054', 'SPAS-15 PBNC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('7', '100003228', 'AUG A3 Camo Soldier', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('7', '601002067', 'C. Phyton PBNC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('8', '702001101', 'Fang Blade PBNC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('8', '1301854000', 'Caixa Camo Soldier', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('9', '601002074', 'Kriss Vector SDP Camo Soldier', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('9', '702015009', 'Faca de Osso PBNC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('10', '300005128', 'Rangemaster .338 Camo Soldier', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('10', '904007051', 'Medical Kit PBNC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('11', '601002123', 'C. Phyton Vacance', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('11', '1105003013', 'ChapÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â©u Camo Soldier', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('12', '200004234', 'P90 M.C Camo Soldier', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('12', '1104003295', 'MÃƒÂ¡scara Vacance', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('13', '300005234', 'Tactilite T2 Vacance', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('13', '702015010', 'Faca de Osso Camo Soldier', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('14', '100003229', 'G36C Camo Soldier', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('14', '300005233', 'Cheytac M200 Vacance', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('15', '200004441', 'Kriss S.V Vacance', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('15', '1301306000', 'Caixa Newborn 2016', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('16', '200004443', 'OA-93 Vacande', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('16', '300005164', 'AS 50 D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('17', '100003367', 'AUG A3 Vacance', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('17', '702001098', 'Hair Dryer', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('18', '100003063', 'AUG A3 E-Sports', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('18', '400006111', 'M1887 Vacance', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('19', '1001002278', 'Chou Pilot', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('19', '1301299000', 'Caixa Blue Diamond', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('20', '1001001275', 'Rica Pilot', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('20', '1301336000', 'Caixa Especial PBIC', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('21', '702001183', 'Butterfly Knife PBWC2017', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('21', '1301895000', 'Caixa Monkey', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('22', '300005140', 'Cheytac M200 Monkey', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('22', '702001190', 'Ballistic Knife Brazuca2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('23', '200004253', 'Kriss S.V Monkey', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('23', '1104003293', 'MÃƒÂ¡scara Brazuca2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('24', '100003194', 'AUG A3 IndependÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Âªncia', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('24', '100003363', 'SC-2010 Brazuca2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('25', '300005228', 'L115A1 Brazuca2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('25', '1300044030', 'Colete 10% 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('26', '1300078030', 'Hollow Point Plus 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('27', '1300026030', 'Troca RÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡pida 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('28', '300005082', 'Barret M82A1 P.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('29', '1300017003', 'Receber Drop 3D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('30', '100003049', 'Famas G2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('31', '200004136', 'OA-93 Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('32', '1300036007', 'MuniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o Hollow Point Fortificada 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('33', '1300036030', 'MuniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o Hollow Point Fortificada 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('34', '1105003016', 'Mascara Monkey', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('35', '1300017030', 'Receber Drop 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('36', '1300031007', 'Bala de Ferro 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('37', '1300031030', 'Bala de Ferro 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('38', '1301335000', 'Caixa Supreme', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('39', '1300017007', 'Receber Drop 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('40', '100003013', 'G36C Ext.', 'UNIDADES', '10');
INSERT INTO "public"."storage" VALUES ('41', '100003014', 'SG-550 Camoflage', 'UNIDADES', '8');
INSERT INTO "public"."storage" VALUES ('42', '100003131', 'AUG A3 LATIN3', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('43', '100003155', 'AUG A3 Brazuca', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('44', '200004026', 'Kriss S.V Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('45', '200004126', 'Kriss S.V Inferno', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('46', '200018006', 'Dual Micro Uzi', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('47', '300005032', 'Barrett M82A1 Qty', 'UNIDADES', '19');
INSERT INTO "public"."storage" VALUES ('48', '1302014000', 'Caixa Woody A', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('49', '100003280', 'AUG A3 Woody', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('50', '100003281', 'XM8 Woody', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('51', '400006075', 'M1887 Woody', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('52', '100003209', 'Vz.52 BlackPearl', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('53', '100003043', 'M4 SR-16 Lv2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('54', '200004024', 'MP5K G D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('55', '1302016000', 'Caixa Woody B', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('56', '200004300', 'OA-93 Woody', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('57', '300005175', 'Cheytac M200 Woody', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('58', '601014020', 'Scorpion Vz.61 Woody', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('59', '100003226', 'SC-2010 Medical', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('60', '702001161', 'Fang Blade Supreme', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('61', '1301901000', 'Caixa Horror', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('62', '100003232', 'AUG A3 Horror', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('63', '1104003214', 'Mascara Horror', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('64', '300005131', 'Cheytac M200 Horror', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('65', '200004239', 'P90 Ext Horror', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('66', '200004237', 'Kriss S.V Horror', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('67', '1301298000', 'Caixa Alien', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('68', '300005006', 'Dragunov G.', 'UNIDADES', '14');
INSERT INTO "public"."storage" VALUES ('69', '300005024', 'PSG1 G.', 'UNIDADES', '16');
INSERT INTO "public"."storage" VALUES ('70', '300005064', 'L115A1 LATIN3', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('71', '702001004', 'Amok Kukri', 'UNIDADES', '28');
INSERT INTO "public"."storage" VALUES ('72', '702001043', 'Machete G.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('73', '702001049', 'Espada Arabe E-Sport', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('74', '803007006', 'C-5 D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('75', '803007004', 'C-5', 'UNIDADES', '40');
INSERT INTO "public"."storage" VALUES ('76', '803007009', 'Granada de Chocolate', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('77', '803007033', 'Granada de Futebol', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('78', '702001002', 'M-9 100U', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('79', '702001021', 'Keris 1D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('80', '601002021', 'Glock 18 D. 7D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('81', '904007005', 'WP Smoke', 'UNIDADES', '42');
INSERT INTO "public"."storage" VALUES ('82', '904007015', 'Kit Medico de Chocolate', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('83', '1301051000', 'Trocar Nome do Cla', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('84', '1301052000', 'Alterar Emblema do Clan', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('85', '601002023', 'IMI Uzi', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('86', '200004106', 'PP-19 Bizon', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('87', '100003048', 'AUG A3 Black', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('88', '100003094', 'SCAR-L Carbine', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('89', '100003115', 'AN-94', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('90', '100003118', 'Pindad Reload', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('91', '100003001', 'SG-550 Ext.', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('92', '100003002', 'AK-47 Ext.', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('93', '100003003', 'M4A1 Ext.', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('94', '100003009', 'F2000 Ext.', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('95', '100003150', 'TAR-21 Midnight', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('96', '100003159', 'AUG A3 PBIC2014', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('97', '200004001', 'MP5 Ext.', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('98', '200004004', 'MP7 Ext.', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('99', '200004008', 'UMP45 Ext.', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('100', '200004009', 'Spectre W.', 'UNIDADES', '20');
INSERT INTO "public"."storage" VALUES ('101', '200004013', 'Kriss S.V', 'UNIDADES', '24');
INSERT INTO "public"."storage" VALUES ('102', '200004021', 'K-1 Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('103', '200004039', 'Kriss S.V Black', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('104', '200004096', 'AKS74U Reload', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('105', '200004097', 'UMP45 Reload', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('106', '200004098', 'Spectre Reload', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('107', '200004099', 'SS1-R5 Reload', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('108', '200004100', 'MP5K Reload', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('109', '601014004', 'Dual-Eagle G.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('110', '200004134', 'OA-93', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('111', '200018004', 'Dual Uzi', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('112', '200018013', 'Dual Mini Uzi G.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('113', '300005015', 'L115A1 G', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('114', '300005026', 'L115A1 Black', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('115', '300005030', 'Cheytac M200', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('116', '300005034', 'DSR-1', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('117', '300005035', 'SVU', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('118', '300005072', 'Walther WA2000', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('119', '300005098', 'DSR-1 D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('120', '400006016', 'SPAS 15 Sl', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('121', '601002011', 'Glock 18', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('122', '601002035', 'MK.23 Reload', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('123', '601002036', 'Desert Eagle Reload', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('124', '601014002', 'Dual D-Eagle', 'UNIDADES', '32');
INSERT INTO "public"."storage" VALUES ('125', '702001017', 'Fang Blade', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('126', '803007027', 'M14 Mine', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('127', '1001002008', 'Leopard', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('128', '1104003018', 'Mascara Jason', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('129', '1104003019', 'Mascara Panda', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('130', '1104003032', 'Mascara Tigre', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('131', '1104003071', 'Mascara Dinossauro', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('132', '100003036', 'AUG A3', 'UNIDADES', '12');
INSERT INTO "public"."storage" VALUES ('133', '100003037', 'AUG A3 G', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('134', '1300035001', 'Explosivo Extra 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('135', '1300038001', 'Gold 100% 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('136', '1300119001', 'Gold 50% 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('137', '1300004001', 'Gold 30% 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('138', '1300003001', 'Exp 50% 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('139', '1300002001', 'Exp 30% 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('140', '1300026001', 'Troca Rapida 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('141', '1300027001', 'Recarregamento rapido 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('142', '300005083', 'Cheytac M200 Brazuca', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('143', '400006037', 'M1887 Brazuca', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('144', '100003050', 'Famas G2 Commando', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('145', '100003096', 'SCAR-L F.C', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('146', '100003111', 'AUG A3 BR Camo', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('147', '100003116', 'F2000 Reload', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('148', '100003117', 'SG550 Reload', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('149', '100003123', 'TAR-21', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('150', '100003015', 'AK SOPMOD', 'UNIDADES', '12');
INSERT INTO "public"."storage" VALUES ('151', '100003143', 'FG-42', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('152', '100003153', 'SC-2010', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('153', '200004010', 'P90 M.C', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('154', '200004011', 'P90 Ext.', 'UNIDADES', '22');
INSERT INTO "public"."storage" VALUES ('155', '200004107', 'MP9 Ext', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('156', '300005005', 'L115A1', 'UNIDADES', '18');
INSERT INTO "public"."storage" VALUES ('157', '200004114', 'P90 Ext. BR Camo', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('158', '300005022', 'Rangemaster 7.62', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('159', '300005081', 'L115A1 GSL2014', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('160', '400006004', '870MCS W.', 'UNIDADES', '35');
INSERT INTO "public"."storage" VALUES ('161', '601002007', 'C. Phyton', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('162', '601002016', 'RB 454 SS8M+S', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('163', '601002049', 'C. Phyton Brazuca', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('164', '601014007', 'Dual HK45', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('165', '702001018', 'Ballistic Knife', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('166', '702001024', 'Alcacuz', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('167', '1001002009', 'Hide', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('168', '702015001', 'Dual Knife', 'UNIDADES', '26');
INSERT INTO "public"."storage" VALUES ('169', '702015002', 'Dual Knife D.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('170', '702015003', 'Faca de Osso', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('171', '1001001010', 'Viper Red +30P', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('172', '1001001015', 'CB DFox', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('173', '1001001017', 'CB ViperRed', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('174', '1001001034', 'Scarlett', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('175', '1001001054', 'Tarantula 2014', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('176', '1001002016', 'CB Leopard', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('177', '1001002018', 'CB Hide', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('178', '1001002033', 'Chou', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('179', '1001002035', 'Reinforced Chou', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('180', '1001002053', 'Hide 2014', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('181', '1006003032', 'Dino Elite', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('182', '1006003044', 'Reinforced Raptor', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('183', '1006003045', 'Reinforced Sting', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('184', '1006003046', 'Reinforced Acid', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('185', '1104003097', 'Mascara Determinado', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('186', '1104003101', 'Mascara Cranio G', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('187', '1104003178', 'Mascara Brazuca', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('188', '1301047000', 'Trocar Nick', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('189', '1300014001', 'Cor nova da mira 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('190', '904007007', 'WP Smoke D.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('191', '702023002', 'Soco Ingles Preto', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('192', '702023003', 'Soco Ingles Brass', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('193', '702023004', 'Soco Ingles Prata', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('194', '702023005', 'Soco Ingles Halloween', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('195', '702023006', 'Soco Ingles Espinhos', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('196', '100003147', 'AUG A3 Inferno', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('197', '702023007', 'Soco Ingles Ballock', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('198', '702001066', 'Foice da Morte', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('199', '200004075', 'P90 Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('200', '200004165', 'OA-93 Xmas', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('201', '200004167', 'PP-19 Xmas', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('202', '200004168', 'Kriss Xmas', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('203', '200018005', 'Dual Mini-Uzi', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('204', '200018007', 'Dual Uzi Silenciada', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('205', '200018009', 'Dual Micro-Uzi Silenciada', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('206', '200018011', 'Dual Mini Uzi Silenciada Sl.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('207', '300005001', 'Dragunov', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('208', '400006018', 'SPAS-15 MSC', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('209', '601002017', 'C. Phyton G', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('210', '601002018', 'C. Phyton G.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('211', '702001007', 'Mini Axe', 'UNIDADES', '29');
INSERT INTO "public"."storage" VALUES ('212', '1301053000', 'Reiniciar Clan Win/Losers', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('213', '702001014', 'Machete C.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('214', '803007020', 'Granada de acucar', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('215', '904007003', 'Flash', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('216', '904007010', 'Smoke Plus', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('217', '904007013', 'Flash Plus', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('218', '904007014', 'Kit Medico Hallonween', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('219', '904007021', 'Kit Medico Lotus', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('220', '1001001003', 'Tarantula', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('221', '1001001007', 'D-Fox +20XP', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('222', '1001001036', 'Reinforced Rica', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('223', '1301085000', 'PB Inspector', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('224', '1102003003', 'Capa. avan.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('225', '1102003006', 'Capa. rastre.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('226', '1102003007', 'Capa. avan. P', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('227', '1102003008', 'Capa. Super', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('228', '1104003006', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara de Fogo', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('229', '1104003010', 'Mascara Abobora', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('230', '1104003016', 'M. Alien Red', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('231', '1104003015', 'M. Alien Blue', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('232', '1104003039', 'Mascara Demonio', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('233', '1301048000', 'Resetar Win/Losers', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('234', '1301049000', 'Resetar Kill/Death', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('235', '1301050000', 'Resetar Desistencias', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('236', '400006020', 'Kel-Tec KSG-15', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('237', '1200080000', '100% XP', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('238', '1200038000', 'Gold 100%', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('239', '1200035000', 'Explosivo Extra', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('240', '1200031000', 'Bala de Ferro', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('241', '1200027000', 'Recarregamento rapido', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('242', '1200026000', 'Troca Rapida', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('243', '1200014000', 'Cor nova da mira', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('244', '1200004000', 'Gold 30%', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('245', '1200003000', 'Exp 50%', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('246', '1200002000', 'Exp 30%', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('247', '1300028001', 'Mega HP 10% 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('248', '1200028000', 'Mega HP', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('249', '1300040001', 'Mega HP 5% 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('250', '1200040000', 'Mega HP 5%', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('251', '1300006001', 'Apelido Colorido', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('252', '1200006000', 'Apelido Colorido', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('253', '1300030001', 'Colete 5% 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('254', '1200030000', 'Colete 5%', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('255', '1300044001', 'Colete 10% 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('256', '1200044000', 'Colete 10%', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('257', '1300032001', 'Hollow Point 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('258', '1200032000', 'Hollow Point', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('259', '1300080001', 'Respawn 100% 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('260', '1300064001', 'Respawn 50% 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('261', '1200064000', 'Respawn 50%', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('262', '1200007000', 'Respawn 30%', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('263', '1300011001', 'O Bom Perdedor 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('264', '1200011000', 'O Bom Perdedor', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('265', '1300008001', '40% de Municao 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('266', '1200008000', '40% de Municao', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('267', '100003051', 'Famas G2 Sniper', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('268', '100003052', 'Famas G2 M203', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('269', '100003068', 'AK-47 F.C Red', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('270', '100003064', 'Famas G2 Commando G.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('271', '100003087', 'Famas G2 Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('272', '200004007', 'MP5K G.', 'UNIDADES', '20');
INSERT INTO "public"."storage" VALUES ('273', '200004022', 'MP7 Sl', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('274', '200004027', 'P90 M.C.S', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('275', '200004050', 'Kriss S.V E-Sport', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('276', '200004113', 'P90 M.C Latin3', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('277', '200004132', 'Kriss S.V Midnight', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('278', '200004130', 'Kriss S.V GSL2014', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('279', '200004139', 'Kriss S.V Brazuca', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('280', '200004146', 'P90 Ext. PBIC2014', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('281', '300005033', 'L115A1 E-Sport', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('282', '300005065', 'L115A1 BR Camo', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('283', '300005079', 'Cheytac M200 Inferno', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('284', '300005087', 'Cheytac M200 PBIC2014', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('285', '400006003', 'SPAS-15', 'UNIDADES', '37');
INSERT INTO "public"."storage" VALUES ('286', '400006010', 'M1887 W.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('287', '400006036', 'Kelten GSL2014', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('288', '400006038', 'M1887 PBIC2014', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('289', '601002002', 'MK.23 Ext', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('290', '601013001', 'P99&HAK', 'UNIDADES', '30');
INSERT INTO "public"."storage" VALUES ('291', '601014001', 'Dual Handgun', 'UNIDADES', '32');
INSERT INTO "public"."storage" VALUES ('292', '702001012', 'Mini Axe D.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('293', '702001051', 'Fang GSL2014', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('294', '702001057', 'Fang Inferno', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('295', '1104003012', 'Golden Mask', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('296', '1104003096', 'Mascara Troll', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('297', '1104003098', 'Mascara Fuu', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('298', '1104003120', 'Mascara da Morte', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('299', '1200119000', 'Gold 50%', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('300', '1300079001', 'Colete 20% 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('301', '1200079000', 'Colete 20%', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('302', '1300037001', 'Exp 100% 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('303', '1200037000', 'Exp 100%', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('304', '1300034001', 'C4 Speed 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('305', '1200034000', 'C4 Speed', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('306', '1200036000', 'Hollow Point Fortificada 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('307', '1300009001', 'Patente Falsa 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('308', '1200009000', 'Patente Falsa', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('309', '1301203000', 'Caixa E-Sports 2', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('310', '100003263', 'AUG A3 E-Sports 2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('311', '300005161', 'Tactilite T2 E-Sports 2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('312', '200004270', 'Kriss S.V E-Sports 2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('313', '100003264', 'Famas G2 M203 E-Sports 2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('314', '702015012', 'Kunai Serpent', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('315', '100003248', 'AUG A3 VeraCruz 2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('316', '300005143', 'Cheytac M200 VeraCruz 2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('317', '601002081', 'RB 454 SS8M+S VeraCruz 2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('318', '100003114', 'M4A1 Elite', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('319', '100003045', 'M4 SR-16 F.C', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('320', '100003127', 'M4A1 S.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('321', '100003023', 'M4A1 G.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('322', '100003021', 'M4A1 Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('323', '100003022', 'M4A1 White', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('324', '1103003007', 'Boina Che-Vermelha', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('325', '1301525000', 'Caixa M4A1 Elite', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('326', '400006006', 'SPAS-15 Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('327', '500010002', 'MK46 Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('328', '100003034', 'G36C Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('329', '601013002', 'P99&HAK Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('330', '300005009', 'PSG-1 Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('331', '1301059000', 'Caixa de Armas', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('332', '1001002004', 'Keen Eyes', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('333', '1301202000', 'Caixa Silence', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('334', '100003249', 'AUG A3 Silence', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('335', '300005144', 'Cheytac M200 Silence', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('336', '200004108', 'Kriss S.V Silence', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('337', '702015011', 'Dual Knife Vera Cruz 2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('338', '1301240000', 'Caixa Cupido', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('339', '100003250', 'AUG A3 Cupido', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('340', '300005147', 'Cheytac M200 Cupido', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('341', '200004017', 'P90 Ext Cupido', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('342', '400006065', 'M1887 Cupido', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('343', '200004005', 'Kriss S.V Cupido', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('344', '1001002067', 'Hide Strike', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('345', '1001001068', 'Viper Kopassus', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('346', '100003193', 'AUG A3 LionFlame', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('347', '300005108', 'Cheytac M200 LionFlame', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('348', '1200005000', 'Clan Color', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('349', '702001122', 'Butterfly Knife', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('350', '300005145', 'PGM Hecate2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('351', '300005123', 'Tactilite T2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('352', '100003184', 'AUG A3 Newborn2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('353', '1300242001', 'Especial Extra 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('354', '1200242000', 'Especial Extra', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('355', '1300078001', 'Hollow Point Plus 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('356', '1200078000', 'Hollow Point Plus', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('357', '601002070', 'R.B 454 PBIC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('358', '702001104', 'Keris PBIC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('359', '1104003209', 'Mascara Premium PBIC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('360', '1104003210', 'Mascara PBIC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('361', '1201091000', 'Ketupat', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('362', '1300029030', 'Invunerabilidade 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('363', '1200029000', 'Invunerabilidade', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('364', '1300033030', 'Anti-Flash 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('365', '1200033000', 'Anti-Flash', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('366', '1300065001', 'Colete 90% 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('367', '100003256', 'AUG A3 Beast', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('368', '300005151', 'Cheytac M200 Beast', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('369', '200004258', 'Kriss S.V Beast', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('370', '400006067', 'M1887 Beast', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('371', '100003195', 'AUG A3 Brasil', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('372', '300005110', 'Cheytac M200 Brasil', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('373', '601002060', 'C. Phyton Brazil', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('374', '702001093', 'Machete de Combate Brasil', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('375', '200004200', 'P90 M.C Brasil', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('376', '1103003017', 'Boina Brasil', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('377', '702015008', 'Kunai', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('378', '200004251', 'OA-93 XMAS2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('379', '100003183', 'AUG A3 GRS2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('380', '300005097', 'Cheytac M200 Cangaceiro', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('381', '702001009', 'M7 Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('382', '1301091005', 'Ketupat', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('383', '100003266', 'AUG A3 PBWC2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('384', '200004273', 'OA-93 PBWC2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('385', '200004275', 'Kriss S.V PBWC2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('386', '200004277', 'P90 Ext PBWC2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('387', '300005163', 'Tactilite T2 PBWC2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('388', '601002087', 'C. Phyton PBWC2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('389', '702001137', 'Amok Kukri PBWC2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('390', '702001138', 'Machete de Combate PBWC2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('391', '803007056', 'C-5 PBWC2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('392', '100003275', 'AUG A3 Dolphin', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('393', '100003190', 'AUG A3 Summer', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('394', '100003178', 'AUG A3 Couple Breaker', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('395', '100003180', 'AUG A3 ANC 2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('396', '100003181', 'AUG A3 Sheep', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('397', '100003154', 'SC-2010 Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('398', '100003169', 'Tar-21 Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('399', '200004161', 'MP9 Ext Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('400', '200004162', 'PP-19 Bizon Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('401', '200004219', 'P90 M.C Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('402', '601002071', 'Glock 18 Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('403', '400006059', 'M1887 Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('404', '100003095', 'SCAR-L Recon', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('405', '1104003196', 'Mascara LionFlame', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('406', '400006005', 'M1887', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('407', '300005125', 'L115A1 Basketball', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('408', '200004170', 'Kriss S.V Couple Breaker', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('409', '200004174', 'P90 M.C Sheep', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('410', '100003277', 'AUG A3 Blue Diamond', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('411', '200004292', 'OA-93 Blue Diamond', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('412', '300005170', 'Cheytac M200 Blue Diamond', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('413', '300005171', 'Tactilite T2 Blue Diamond', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('414', '400006073', 'SPAS-15 Blue Diamond', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('415', '601002089', 'MK.23 Blue Diamond', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('416', '1104003237', 'Mascara Blue Diamond', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('417', '1301619000', 'Mix PBIC', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('418', '300005090', 'Cheytac M200 Tiger', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('419', '601013008', 'C. Phython Cutlass', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('420', '400006061', 'M1887 Medical', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('421', '300005076', 'Dragunov Elite', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('422', '100003165', 'AUG A3 Toy', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('423', '100003223', 'M14-EBR', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('424', '300005104', 'Cheytac M200 Ongame', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('425', '200004227', 'P90 Ext Basketball', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('426', '1300035003', 'Explosivo Extra 3D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('427', '904007011', 'Medical Kit', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('428', '1301561000', 'Pacote de Armas CM', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('429', '1104003182', 'Mascara PBIC2014', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('430', '100003120', 'AUG A3 PBIC2013', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('431', '100003071', 'AUG A3 IC', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('432', '200004103', 'Kriss S.V PBIC2013', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('433', '200004060', 'Kriss S.V IC', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('434', '100003121', 'M4A1 PBIC2013', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('435', '100003122', 'AK-47 PBIC2013', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('436', '601014005', 'Dual Handgun D.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('437', '601002024', 'Kriss S.V Vector SDP', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('438', '702001011', 'Amok Kukri D.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('439', '702001058', 'Chinese Cleaver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('440', '100003149', 'AUG A3 GSL2014', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('441', '702001147', 'Karambit', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('442', '1301153000', 'Caixa Sakura', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('443', '100003251', 'AUG A3 Sakura', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('444', '300005148', 'Cheytac M200 Sakura', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('445', '100003252', 'AK SOPMOD Sakura', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('446', '200004115', 'Kriss S.V Sakura', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('447', '601002082', 'C. Phyton Sakura', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('448', '200004220', 'P90 M.C VeraCruz', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('449', '300005055', 'Cheytac M200 GSL', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('450', '1301121000', 'Gold Bomb Premium', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('451', '1301117000', '10K de Gold', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('452', '1301300000', 'Caixa Dolphin', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('453', '1301326000', 'Caixa DarkSteel', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('454', '100003295', 'AUG A3 DarkSteel', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('455', '200004318', 'Kriss S.V DarkSteel', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('456', '200004320', 'P90 Ext DarkSteel', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('457', '300005185', 'Cheytac M200 DarkSteel', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('458', '200004216', 'Kriss S.V PBIC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('459', '300005120', 'Cheytac M200 PBIC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('460', '400006058', 'M1887 PBIC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('461', '200004218', 'P90 Ext PBIC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('462', '400006080', 'M1887 DarkSteel', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('463', '601002098', 'C. Phyton DarkSteel', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('464', '702001159', 'Fang Blade DarkSteel', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('465', '100003301', 'AK-12 Georgeous', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('466', '100003302', 'AUG A3 Georgeous', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('467', '200004330', 'Kriss S.V Georgeous', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('468', '200004332', 'OA-93 Georgeous', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('469', '601014021', 'Scorpion Vz.61 Georgeous', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('470', '400006083', 'M1887 Georgeous', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('471', '400006084', 'Cerberus Georgeous', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('472', '601002094', 'U22 Neos', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('473', '300005068', 'XM2010', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('474', '1200010000', 'Apelido Temporario', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('475', '904007059', 'Smoke Azul', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('476', '1301212000', 'Caixa Dragon', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('477', '100003265', 'AUG A3 Dragon', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('478', '300005162', 'Tactilite T2 Dragon', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('479', '200004272', 'P90 Ext Dragon', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('480', '400006070', 'M1887 Dragon', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('481', '702001127', 'Fang Blade Sakura', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('482', '1300242005', 'Especial Extra 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('483', '1300119005', 'Gold 50% 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('484', '702015015', 'Dual Sword', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('485', '300005146', 'PGM Hecate2 G', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('486', '300005132', 'Tactilite T2 G', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('487', '1300079005', 'Colete 20% 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('488', '1300078005', 'Hollow Point Plus 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('489', '1300065005', 'Colete 90% 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('490', '1300064005', 'Respawn 50% 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('491', '1300044005', 'Colete 10% 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('492', '1300040005', 'Mega HP 5% 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('493', '1300038005', 'Gold 100% 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('494', '1300037005', 'Exp 100% 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('495', '1300036005', 'Hollow Point Fortificada 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('496', '1300035005', 'Explosivo Extra 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('497', '1300034005', 'C4 Speed 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('498', '1300032005', 'Hollow Point 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('499', '1300031005', 'Bala de Ferro 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('500', '1300030005', 'Colete 5% 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('501', '1300028005', 'Mega HP 10% 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('502', '300005014', 'Dragunov Gold D.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('503', '1300027005', 'Recarregamento rapido 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('504', '1300026005', 'Troca Rapida 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('505', '1300017005', 'Pegar arma do oponente 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('506', '1300014005', 'Cor nova da mira 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('507', '1300011005', 'O Bom Perdedor 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('508', '1300010001', 'Apelido Temporario', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('509', '1300009005', 'Patente Falsa 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('510', '1300007001', 'Respawn 30% 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('511', '1300005001', 'Clan Color', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('512', '300005168', 'Cheytac M200 Dolphin', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('513', '1301211000', 'Caixa Mummy', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('514', '100003267', 'AUG A3 Mummy', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('515', '702001139', 'Amok Kukri Mummy', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('516', '200004278', 'Kriss S.V Mummy', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('517', '803007057', 'Granada Mummy', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('518', '1104003234', 'Mask Mummy', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('519', '500010014', 'Ultimax 100 Mummy', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('520', '200004291', 'P90 Ext Dolphin', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('521', '200004289', 'Kriss S.V Dolphin', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('522', '100003276', 'SC-2010 Dolphin', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('523', '300005169', 'L115A1 Dolphin', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('524', '400006072', 'M1887 Dolphin', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('525', '702001140', 'Mini Axe Poison', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('526', '1300006005', 'Apelido Colorido 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('527', '1300005005', 'Nome do Clan Colorido 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('528', '1300004005', 'Gold 30% 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('529', '1300003005', 'Exp 50% 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('530', '1300002005', 'Exp 30% 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('531', '1200185000', '10% de municao', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('532', '100003284', 'Groza', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('533', '100003236', 'K2C', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('534', '100003177', 'XM8 Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('535', '1103003011', 'PB Black Beret', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('536', '110010030', 'TESTE SET2', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('537', '100003217', 'AUG A3 DarkDays', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('538', '200004212', 'Kriss S.V DarkDays', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('539', '300005118', 'Cheytac M200 DarkDays', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('540', '601002069', 'Kriss Vector SDP DarkDays', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('541', '904007061', 'Smoke Amarela', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('542', '1200077000', 'Respawn 20%', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('543', '1300077005', 'Respawn 20% 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('544', '100003271', 'AUG A3 ID 1stAnni', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('545', '200004280', 'Kriss S.V ID 1stAnni', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('546', '300005165', 'Cheytac M200 ID 1stAnni', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('547', '400006071', 'M1887 ID 1stAnni', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('548', '200004282', 'P90 Ext 1stAnni', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('549', '904007022', 'WP Smoke P.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('550', '100003246', 'AK 12', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('551', '100003119', 'AK 47 Elite', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('552', '300005096', 'Cheytac M200 LATIN4', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('553', '400006043', 'Remington ETA', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('554', '702001069', 'Ice Fork', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('555', '1104003248', 'Mascara Clown BR', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('556', '1104003250', 'Mascara Chain', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('557', '1104003235', 'ID 1stAnni Mask', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('558', '100003174', 'XM8', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('559', '1300007005', 'Respawn 30%', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('560', '100003285', 'Groza G.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('561', '1300010005', 'Apelido Temporario', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('562', '1104003009', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara No Alvo', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('563', '1301152000', 'Caixa Beast', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('564', '300005121', 'Cheytac M200 Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('565', '1103003016', 'Boina de General', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('566', '601002012', 'C. Phyton D.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('567', '300005176', 'Tactilite T2 Newborn 2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('568', '702001150', 'Machete de Combate Newborn 2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('569', '1300080005', '100% XP 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('570', '1300029005', 'Invunerabilidade', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('571', '601002088', 'Glock 18 Mummy', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('572', '1300185005', '10% de municao 5D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('573', '702001144', 'Keris ID 1stAnni', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('574', '1001002062', 'Acid Pol Infectado', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('575', '1001002064', 'Hide Infectada', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('576', '1001002065', 'Leopard Infectado', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('577', '100003268', 'Pindad SS2 V5', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('578', '300005177', 'Tactilite T2 Puzzle', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('579', '300005157', 'AS-50', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('580', '100003259', 'AUG-A3 Tiger-Normal', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('581', '300005155', 'Cheytac M200 Tiger-Normal', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('582', '702001132', 'Fang Blade Tiger-Normal', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('583', '803007054', 'K-413 Tiger-Normal', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('584', '100003260', 'AUG-A3 Tiger-Deluxe', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('585', '300005156', 'Cheytac M200 Tiger-Deluxe', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('586', '1300029001', 'Invunerabilidade', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('587', '1300031001', 'Bala de Ferro 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('588', '1104003014', 'Mascara Palhaco', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('589', '1300008005', '40% de Municao', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('590', '1200017000', 'Pegar arma do oponente', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('591', '1300036001', 'Hollow Point Fortificada 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('592', '200004020', 'Spectre Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('593', '1103003018', 'Boina Vera Cruz 2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('594', '100003219', 'AUG A3 PBIC2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('595', '100003287', 'SC-2010 Dracula', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('596', '100003279', 'AUG A3 Alien', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('597', '1301210000', 'Caixa PBWC2016', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('598', '200004043', 'SS1-R5 Carabine', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('599', '200004164', 'MP9 Xmas', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('600', '400006042', 'Zombie Slayer', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('601', '1105003004', 'Chapeu de Cowboy', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('602', '803007008', 'K-413', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('603', '803007018', 'K-413 G', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('604', '1301097000', '???', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('605', '1001002063', 'Keen Eyes Infectada', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('606', '200004288', 'P90 Ext Demonic', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('607', '300005167', 'Cheytac M200 Demonic', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('608', '702001133', 'Fang Blade Tiger-Deluxe', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('609', '803007055', 'K-413 Tiger-Deluxe', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('610', '1104003230', 'Mascara Tiger-Deluxe', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('611', '300005184', 'AS 50 PBTC2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('612', '300005183', 'Cheytac M200 PBTC2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('613', '1301241000', 'Caixa Demonic', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('614', '1300017001', 'Pegar arma do oponente 1D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('615', '904007058', 'Smoke Rosa', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('616', '300005091', 'Cheytac M200 G E-Sport', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('617', '100003274', 'AUG A3 Demonic', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('618', '200004286', 'Kriss S.V Demonic', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('619', '702001129', 'Keris Beast', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('620', '100003099', 'AK SOPMOD GRS', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('621', '300005012', 'Dragunov CG.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('622', '601014011', 'Dual D-Eagle GRS', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('623', '1301055000', '+50 Membros no clan', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('624', '100003054', 'AK-47 G.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('625', '300005159', 'AS-50 G', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('626', '400006015', '870MSC D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('627', '702001025', 'Field Shovel', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('628', '601002013', 'R.B 454 SS2M', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('629', '601002014', 'R.B 454 SS5M', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('630', '601002015', 'R.B 454 SS8M', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('631', '1104003007', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Duas Cores', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('632', '1300027003', 'Recarregamento Rapido 3D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('633', '1300031003', 'Bala de Ferro 3D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('634', '1300026003', 'Troca Rapida 3D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('635', '1300044003', 'Colete 10% 3D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('636', '1300034003', 'C4 Speed 3D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('637', '1300011003', 'O Bom Perdedor 3D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('638', '1300064003', 'Respawn 50% 3D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('639', '100003040', 'AUG A3 D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('640', '1300035030', 'Explosivo Extra 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('641', '300005099', 'Cheytac M200 Couple Breaker', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('642', '100003300', 'AUG A3 Halloween 2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('643', '702001162', 'Halloween Hammer', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('644', '200004328', 'OA-93 Halloween 2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('645', '300005189', 'Cheytac M200 Halloween 2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('646', '1302017000', 'Caixa Halloween 2016', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('647', '1001002052', 'Leopard Skull', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('648', '1001002012', 'Reinforced Leopard', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('649', '1001001011', 'Reinforced D-Fox', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('650', '100003093', 'AUG A3 Blaze', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('651', '1300027030', 'Recarregamento Rapido 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('652', '1103003006', 'Boina Negra', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('653', '1103003014', 'Boina Skull', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('654', '1301852000', 'Caixa de Personagens A', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('655', '1301794000', 'Caixa PBIC2015', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('656', '1301120000', 'Gold Bomb Basic', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('657', '1301154000', 'Caixa Serpent', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('658', '100003253', 'AUG A3 Serpent', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('659', '300005149', 'Cheytac M200 Serpent', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('660', '200004118', 'P90 Ext Serpent', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('661', '200004116', 'Kriss S.V Serpent', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('662', '1301853000', 'Caixa de Personagens B', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('663', '1001002014', 'Reinforced Hide', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('664', '1001001013', 'Reinforced Viper Red', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('665', '1102003002', 'Capacete Comum', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('666', '100003029', 'G36C Ext Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('667', '300005010', 'Dragunov Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('668', '100003011', 'K-201', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('669', '300005008', 'SSG 69 Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('670', '100003019', 'SG-550 Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('671', '200004014', 'MP5K Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('672', '200018008', 'Mini Uzi Dupla Silenciada', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('673', '100003258', 'AUG A3 GSL2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('674', '601002084', 'C. Phyton GSL2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('675', '200004263', 'Kriss S.V GSL2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('676', '300005153', 'Cheytac M200 GSL2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('677', '300005154', 'Tactilite T2 GSL2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('678', '702001149', 'Fang Blade Alien', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('679', '702001143', 'Amok Kukri Poison', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('680', '300005173', 'Cheytac M200 Alien', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('681', '200004297', 'P90 Ext Alien', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('682', '300005174', 'L115A1 Alien', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('683', '200004298', 'Kriss S.V Alien', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('684', '100003282', 'Famas G2 Newborn2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('685', '601002092', 'C. Python Newborn2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('686', '200004302', 'OA-93 Newborn2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('687', '400006076', 'M1887 Newborn2016', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('688', '100003299', 'AUG A3 Supreme', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('689', '601002100', 'C. Python Supreme', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('690', '200004325', 'Kriss S.V Supreme', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('691', '300005188', 'Cheytac M200 Supreme', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('692', '200004327', 'P90 Ext Supreme', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('693', '400006082', 'M1887 Supreme', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('694', '601002091', 'MK.23 Alien', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('695', '1104003129', 'Mascara PBIC 2013', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('696', '1104003107', 'Mascara PBIC 2012', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('697', '1300036003', 'Hollow Point Fortificada 3D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('698', '1300032003', 'Hollow Point 3D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('699', '200004076', 'AKS74U', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('700', '200004138', 'P90 Ext Brazuca', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('701', '300005021', 'Rangemaster .338', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('702', '300005023', 'Rangemaster 7.62 STBY', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('703', '1104003013', 'Mascara de CrÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¢nio', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('704', '1105003001', 'Gorro Noel', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('705', '1105003009', 'BonÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â© Pirocoptero', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('706', '1301666000', 'Token Dourado', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('707', '1104003034', 'Rorschach Mask', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('708', '1104003078', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Chinesa Vermelha', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('709', '1104003079', 'Red Eyes Mask', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('710', '1301305000', 'Caixa GSL2016', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('711', '300005139', 'Tactilite T2 XMAS2015', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('712', '100003157', 'AUG A3 Champion', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('713', '100003148', 'AUG A3 PBNC5', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('714', '100003128', 'AUG A3 Azerbaijan', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('715', '100003158', 'AUG A3 W.O.E', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('716', '100003171', 'AUG A3 Latin4', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('717', '100003164', 'AUG A3 G E-Sport', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('718', '100003188', 'AUG A3 Ongame', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('719', '100003192', 'AUG A3 Rose', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('720', '702001052', 'Fang Blade Brazuca', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('721', '702001041', 'Arabian Sword', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('722', '1001002051', 'Hide Kopassus', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('723', '904007012', 'WP Smoke Plus', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('724', '1001002019', 'Reinforced Acil Pol', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('725', '601002029', 'GL-06', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('726', '500010011', 'Ultimax-100', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('727', '400006039', 'UTS-15', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('728', '200004159', 'OA-93 D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('729', '702001064', 'Badminton Racket', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('730', '400006017', 'M1887 D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('731', '601002005', 'D-Eagle Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('732', '601014006', 'Dual D-Eagle D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('733', '1103003001', 'Boina de Assalto', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('734', '1103003003', 'Boina de Snipers', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('735', '1103003002', 'Boina de SMG', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('736', '1103003004', 'Boina de Shotgun', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('737', '1103003005', 'Boina de Pistoleiro', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('738', '400006041', 'M1887 Lion-Heart', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('739', '100003086', 'AK-47 Goddess', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('740', '803007035', 'M-14 Mine D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('741', '1104003008', 'Hockey Mask', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('742', '1300185007', '10% de MuniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('743', '200004144', 'Kriss S.V W.O.E', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('744', '300005085', 'Cheytac M200 W.O.E', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('745', '300005086', 'Dragunov W.O.E', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('746', '1104003023', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Brasil', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('747', '702015014', 'Faca de Osso E-Sports2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('748', '1301663000', 'Caixa Everyday Login Box', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('749', '1104003233', 'Mascara E-Sports2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('750', '500010013', 'Ultimax 100 Madness', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('751', '601014018', 'Scorpion Vz.61 G.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('752', '803007040', 'M18A1 Claymore', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('753', '1300040030', 'Mega HP 5% 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('754', '100003058', 'AK-47 F.C', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('755', '1103003009', 'Boina Che Guevara', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('756', '1300028030', 'Mega HP 10% 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('757', '100016001', 'RPG', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('758', '100003191', 'SC-2010 Rose', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('759', '200004195', 'P90 M.C Rose', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('760', '300005107', 'Dragunov Rose', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('761', '601002059', 'C. Phyton Rose', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('762', '702001084', 'Machete de Combate Rose', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('763', '1102003009', 'Aureola de Anjo', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('764', '1105003012', 'ChapÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â©u da IndependÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Âªncia', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('765', '100003057', 'Vz.52', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('766', '601002022', 'Colt 45', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('767', '300005031', 'Winchester M70', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('768', '1103003008', 'Boina Che Guevara Yellow', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('769', '110010130', 'Boina Che Guevara Red', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('770', '100003062', 'FAMAS G2 E-Sports', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('771', '400006047', 'Cerberus', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('772', '702001109', 'Ballistic Knife Obisidian', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('773', '1301664000', 'Everyday Login Box Plus', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('774', '1300064030', 'Respawn 50% 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('775', '100003257', 'AUG A3 PBGC', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('776', '200004260', 'Kriss S.V PBGC', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('777', '200004262', 'P90 Ext PBGC', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('778', '300005152', 'Cheytac M200 PBGC', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('779', '400006068', 'M1887 PBGC', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('780', '702001130', 'Fang Blade PBGC', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('781', '1104003228', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara PBGC', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('782', '100003004', 'K-2', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('783', '200004006', 'K-1', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('784', '300005003', 'SSG69', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('785', '400006001', '870MCS', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('786', '601002003', 'K-5', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('787', '702001001', 'M7', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('788', '702023001', 'Barefirst', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('789', '803007001', 'K-400', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('790', '904007002', 'Smoke', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('791', '1001001005', 'Red Bulls', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('792', '1001002006', 'Acid Pol', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('793', '1102003001', 'Capacete BÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡sico', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('794', '1006003041', 'Raptor', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('795', '1006003042', 'Sting', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('796', '1006003043', 'Acid', 'PERMANENTE', '0');
INSERT INTO "public"."storage" VALUES ('797', '100003303', 'AUG A3 Mistic', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('798', '702001120', 'Monkey Hammer', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('799', '100003010', 'M4A1 Camoflage with Silencer', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('800', '100003017', 'AK-47 Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('801', '100003032', 'F2000 Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('802', '200004023', 'UMP45 Sl.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('803', '300005004', 'SSG-69 Camoflage', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('804', '400006008', '870MCS SL ', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('805', '100003035', 'SG-550 S D.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('806', '100003038', 'G36C Ext. D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('807', '100003039', 'AK SOPMOD D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('808', '200004025', 'Spectre W D.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('809', '200004029', 'P90 Ext D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('810', '300005016', 'PSG1 S. D ', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('811', '300005017', 'L115A1 D.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('812', '400006011', '870MCS W. D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('813', '400006012', 'SPAS-15 D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('814', '601013003', 'P99 & HAK D. ', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('815', '200004032', 'P90 M.C D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('816', '300005011', 'Dragunov CS. ', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('817', '1104003003', 'Camuflagem Tigre Russo', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('818', '1104003004', 'Camuflagem Naval', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('819', '1104003005', 'Camuflagem Francesa', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('820', '1300002007', 'Exp 30% 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('821', '1300002030', 'Exp 30% 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('822', '1300004007', 'Gold 30% 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('823', '1300004030', 'Gold 30% 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('824', '1300007007', 'Respawn 30% 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('825', '1300007030', 'Respawn 30% 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('826', '1300008007', '40% de MuniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('827', '1300008030', '40% de MuniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('828', '1104003011', 'Pink Death Mask', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('829', '1301056000', 'Reiniciar Pontos Clan', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('830', '1300032007', 'MuniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o Hollow Point 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('831', '1300032030', 'MuniÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o Hollow Point 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('832', '1300030007', 'Colete 5% 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('833', '1300030030', 'Colete 5% 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('834', '1300035007', 'Explosivo Extra 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('835', '1300064007', 'Respawn 50% 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('836', '1300040007', 'Mega HP 5% 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('837', '601002001', 'Desert Eagle', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('838', '100003005', 'NÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢O SEI', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('839', '500010001', 'MK46 Ext. ', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('840', '300005002', 'PSG-1', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('841', '300005007', 'PSG-1 Silver', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('842', '200004002', 'Spectre Ext.', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('843', '1300003007', 'Exp 50% 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('844', '1300003030', 'Exp 50% 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('845', '300005029', 'VSK94', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('846', '100003041', 'AK SOPMOD CG', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('847', '300005020', 'M4 SRP Lvl-3', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('848', '1104003017', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara TemplÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡ria', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('849', '1300119007', 'Gold 50% 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('850', '1300119030', 'Gold 50% 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('851', '1300044007', 'Colete 10% 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('852', '200004038', 'MP7 Camoflage', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('853', '100003053', 'SS2-V4 Para Sniper', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('854', '500010003', 'RPD', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('855', '200004034', 'M4 CQB-R lvl-1', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('856', '200004035', 'M4 CQB-R lvl-2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('857', '300005018', 'M4 SPR Lvl-1', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('858', '300005019', 'M4 SRP Lvl-2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('859', '100003042', 'M4 SR-16 lvl-1', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('860', '1300027007', 'Recarregamento RÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡pido 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('861', '1300026007', 'Troca RÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡pida 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('862', '1300037007', 'Exp 100% 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('863', '1300037030', 'Exp 100% 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('864', '1300038007', 'Gold 100% 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('865', '1300038030', 'Gold 100% 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('866', '1300080007', 'Respawn 100% 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('867', '1300080030', 'Respawn 100% 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('868', '500010004', 'L86 LSW ', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('869', '1300011007', 'O Bom perdedor 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('870', '1300011030', 'O Bom perdedor 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('871', '1301115000', '1000 pontos', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('872', '200004033', 'AKS47U Ext.', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('873', '300005036', 'L115A1 SE', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('874', '100003102', 'HK-417', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('875', '500010007', 'RPD Silver', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('876', '601002028', 'M79', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('877', '601013007', 'P99 & HAK Reload', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('878', '1300014007', 'Cor nova da mira 7d', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('879', '1300014030', 'Cor nova da mira 30D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('880', '100003069', 'SCAR-H CQC', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('881', '601002027', 'MK.23 Silver', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('882', '300005059', 'PSG-1 Reload', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('883', '300005060', 'Rangemaster .338 Reload', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('884', '1104003021', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara da Morte', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('885', '100003170', 'SCAR-L Carbine Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('886', '300005094', 'SVU Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('887', '200004036', 'M4 CQB-R lvl-3', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('888', '300005095', 'DSR-1 Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('889', '100003168', 'HK-417 Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('890', '702001059', 'Machete White', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('891', '400006048', 'UTS-15 G.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('892', '1104003192', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Ongame Premium', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('893', '1104003187', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Latin4', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('894', '1104003136', 'Unicorn Mask', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('895', '100003221', 'AK SOPMOD Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('896', '1300028007', 'Mega HP 10% 7D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('897', '601014017', 'Scorpion Vz.61', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('898', '702001023', 'Keris S.', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('899', '300005191', 'Cheytac M200 Mistic', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('900', '601002010', 'Colt Python Gold+', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('901', '1300079007', 'Colete 20% 7D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('902', '1001002027', 'Hide', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('903', '1001001028', 'Viper Red', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('904', '100003104', 'AUG A3 GSL', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('905', '300005092', 'L115A1 Toy', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('906', '100003334', 'AUG A3 Salvation', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('907', '200004377', 'Kriss S.V Salvation', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('908', '200004379', 'OA-93 Salvation', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('909', '300005208', 'Tactilite T2 Salvation', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('910', '100003344', 'AUG A3 Naga', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('911', '200004393', 'Kriss S.V Naga', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('912', '200004395', 'P90 Ext Naga', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('913', '300005214', 'Cheytac M200 Naga', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('914', '400006100', 'M1887 Naga', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('915', '601002116', 'C. Python Naga', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('916', '601013004', 'P99 & HAK Sl. D ', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('917', '100003338', 'MSBS', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('918', '100003340', 'MSBS Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('919', '601002114', 'TEC-9 Gold', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('920', '300005232', 'Barrett M82A1 Premium', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('921', '702001184', 'Karambit CNPB T5', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('922', '1001001286', 'Viper Capitain', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('923', '702001188', 'Karambit Infinitum', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('924', '300005202', 'Tactilite T2 Fire', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('925', '300005229', 'Cheytac M200 Brazuca2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('926', '100003362', 'AUG A3 Brazuca2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('927', '601002121', 'P08 Luger Bracuza2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('928', '100003352', 'AUG A3 CNPB T5', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('929', '300005220', 'Cheytac M200 CNPB T5', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('930', '200004412', 'P90 Ext. CNPB T5', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('931', '1104003287', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara CNPB T5', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('932', '100003345', 'AUG A3 Comic', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('933', '100003346', 'AK-47 Comid', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('934', '200004396', 'Kriss S.V ComicD', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('935', '300005215', 'Cheytac M200 Comic', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('936', '400006101', 'M1887 Comic', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('937', '601002117', 'Desert Eagle Comic', 'DIA', '0');
INSERT INTO "public"."storage" VALUES ('938', '702001181', 'Amok Kukri Comic', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('939', '1001002175', 'Undercover Hide', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('940', '1001001087', 'Gengster Viper Red', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('941', '100003325', 'AUG A3 Mech Hero', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('942', '200004362', 'OA-93 Mech Hero', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('943', '300005201', 'Tactilite T2 Mech Hero', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('944', '400006091', 'Remington ETA Mech Hero', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('945', '601002110', 'Kriss Vector SDP Mech Hero', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('946', '1104003274', 'MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡scara Mech Hero', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('947', '1300006010', 'Apelido Colorido 10D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('948', '1105003005', 'Bandana 100U', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('949', '100003261', 'AUG A3 Midnight2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('950', '300005158', 'Cheytac M200 Midnight2', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('951', '1300009007', 'Patente Falsa 7D', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('952', '100003262', 'AUG A3 Skelleton', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('953', '200004267', 'Kriss S.V Skelleton', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('954', '200004269', 'P90 Ext. Skelleton', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('955', '300005160', 'Cheytac M200 Skelleton', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('956', '702015013', 'Faca de Osso Skelleton', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('957', '300005052', 'Cheytac M200 GRS', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('958', '100003097', 'AUG A3 GRS', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('959', '100003098', 'M4A1 GRS', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('960', '100003100', 'Famas GRS', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('961', '200004081', 'MP7 GRS', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('962', '200004082', 'P90 M.C GRS', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('963', '200004083', 'Kriss S.V GRS', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('964', '400006026', 'M1887 W GRS', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('965', '702001037', 'Ballistic Knife GRS', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('966', '702015005', 'Faca de Osso GRS', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('1058', '200004163', 'P90 Ext. LATIN4', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('1059', '702001067', 'Fang Blade LATIN4', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('1060', '200004228', 'OA-93 Medical', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('1061', '100003197', 'AUG A3 4Game', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('1062', '100003225', 'AK-47 SOPMOD Medical', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('1063', '300005126', 'Cheytac M200 Medical', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('1064', '200004230', 'P90 Ext. Medical', 'DIAS', '0');
INSERT INTO "public"."storage" VALUES ('1065', '1300034010', 'C4 Speed 10D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('1066', '1300242010', 'Especial Extra 10D', 'UNIDADES', '0');
INSERT INTO "public"."storage" VALUES ('1067', '1300029003', 'Invunerabilidade 3D', 'UNIDADES', '0');

-- ----------------------------
-- Table structure for system_blocked
-- ----------------------------
DROP TABLE IF EXISTS "public"."system_blocked";
CREATE TABLE "public"."system_blocked" (
"ip" varchar(255) COLLATE "default" DEFAULT ''::character varying NOT NULL,
"mac" varchar(255) COLLATE "default" DEFAULT ''::character varying NOT NULL,
"coment" varchar(255) COLLATE "default" DEFAULT ''::character varying
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of system_blocked
-- ----------------------------
INSERT INTO "public"."system_blocked" VALUES ('', '12:34:A1:B4:00:A0', 'AutoBan: Intervalo de login incorreto');
INSERT INTO "public"."system_blocked" VALUES ('168.181.49.62', '', '');
INSERT INTO "public"."system_blocked" VALUES ('177.220.174.117', 'D0:50:99:AF:04:A8', 'ban');
INSERT INTO "public"."system_blocked" VALUES ('179.199.16.172', '70:85:C2:08:3C:1B', 'ban');
INSERT INTO "public"."system_blocked" VALUES ('187.75.14.19', 'D0:50:99:A2:75:91', 'ban');

-- ----------------------------
-- Table structure for system_network
-- ----------------------------
DROP TABLE IF EXISTS "public"."system_network";
CREATE TABLE "public"."system_network" (
"id" int8 DEFAULT nextval('ipsystem_seq'::regclass) NOT NULL,
"type" varchar(5) COLLATE "default" NOT NULL,
"startpoint" varchar(15) COLLATE "default" NOT NULL,
"endpoint" varchar(15) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of system_network
-- ----------------------------
INSERT INTO "public"."system_network" VALUES ('464', '60', '600', '8000');
INSERT INTO "public"."system_network" VALUES ('501', '100', '600', '8000');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table jogador
-- ----------------------------
ALTER TABLE "public"."jogador" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table jogador_inventario
-- ----------------------------
ALTER TABLE "public"."jogador_inventario" ADD PRIMARY KEY ("object", "player_id");

-- ----------------------------
-- Primary Key structure for table system_network
-- ----------------------------
ALTER TABLE "public"."system_network" ADD PRIMARY KEY ("id");
