import { pgTable, serial, text } from 'drizzle-orm/pg-core';
/**CREATE TABLE public.users (
	id serial4 NOT NULL,
	email varchar(255) NOT NULL,
	"name" varchar(255) NULL,
	username varchar(255) NULL,
	password_hash varchar(255) NOT NULL,
	"role" varchar NOT NULL DEFAULT 'user'::character varying,
	CONSTRAINT users_pkey PRIMARY KEY (id)
); */
export const users = pgTable('users', {
  id: serial('id').primaryKey(),
  username: text('username').notNull().unique(),
  email: text('email').notNull(),
  name: text('name'),
  role: text('role').notNull().default('user'),
  passwordHash: text('password_hash').notNull()
});

export type SelectUser = typeof users.$inferSelect;
