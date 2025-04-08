import {
    pgTable,
    text,
    serial,
    numeric
  } from 'drizzle-orm/pg-core';
import { relations } from 'drizzle-orm';
import { creditos } from './credito';

export const productos = pgTable('producto', {
    id: serial('id').primaryKey(),
    nombre: text('nombre').notNull(),
    descripcion: text('descripcion'),
    precio: numeric('precio', { precision: 10, scale: 2 }).notNull(),
  });

  export const productoRelations = relations(productos, ({ many }) => ({
    creditos: many(creditos),
  }));


  export type SelectProducto = typeof productos.$inferSelect;