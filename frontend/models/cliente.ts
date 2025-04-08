import {
  pgTable,
  text,
  numeric,
  integer,
  timestamp,
  pgEnum,
  serial,
  boolean
} from 'drizzle-orm/pg-core';
import { relations } from 'drizzle-orm';
import { creditos } from './credito';

export const statusClienteEnum = pgEnum('status', ['activo', 'inactivo', 'suspendido']);

export const clientes = pgTable('cliente', {
  id: serial('id').primaryKey(),
  nombre: text('nombre').notNull(),
  cedula: text('cedula').notNull().unique(),
  direccion: text('direccion'),
  telefono: text('telefono'),
  es_nuevo: boolean('es_nuevo').default(true),
  tope_credito: numeric('tope_credito', { precision: 12, scale: 2 }).default('250000'),
  estado: statusClienteEnum('estado').default('activo')
});

export const clienteRelations = relations(clientes, ({ many }) => ({
  creditos: many(creditos),
}));


export type SelectCliente = typeof clientes.$inferSelect;