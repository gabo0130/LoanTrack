import { pgTable, serial, integer, date, numeric, varchar } from "drizzle-orm/pg-core";
import { relations } from "drizzle-orm";
import { cuotas } from "./cuota";

export const pagos = pgTable("pago", {
  id: serial("id").primaryKey(),
  id_cuota: integer("id_cuota").notNull().references(() => cuotas.id),
  fecha_pago: date("fecha_pago").notNull(),
  monto_pagado: numeric("monto_pagado", { precision: 12, scale: 2 }).notNull(),
  metodo_pago: varchar("metodo_pago", { length: 50 }),
});

export const pagoRelations = relations(pagos, ({ one }) => ({
  cuota: one(cuotas, {
    fields: [pagos.id_cuota],
    references: [cuotas.id],
  }),
}));

export type SelectPago = typeof pagos.$inferSelect;
export type InsertPago = typeof pagos.$inferInsert;
