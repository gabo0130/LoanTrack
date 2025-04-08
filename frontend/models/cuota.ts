import { pgTable, serial, integer, numeric, date, text, pgEnum } from "drizzle-orm/pg-core";
import { relations } from "drizzle-orm";
import { creditos } from "./credito";
import { pagos } from "./pago";

export const estadoCuotaEnum = pgEnum("estado_cuota_enum", ["pendiente", "pagado", "vencido"]);

export const cuotas = pgTable("cuota", {
  id: serial("id").primaryKey(),
  id_credito: integer("id_credito").notNull().references(() => creditos.id),
  numero_cuota: integer("numero_cuota").notNull(),
  monto_cuota: numeric("monto_cuota", { precision: 12, scale: 2 }).notNull(),
  fecha_vencimiento: date("fecha_vencimiento").notNull(),
  estado: estadoCuotaEnum("estado").default("pendiente"),
  saldo_pendiente: numeric("saldo_pendiente", { precision: 12, scale: 2 }),
});

export const cuotaRelations = relations(cuotas, ({ one, many }) => ({
  credito: one(creditos, {
    fields: [cuotas.id_credito],
    references: [creditos.id],
  }),
  pagos: many(pagos),
}));

export type SelectCuota = typeof cuotas.$inferSelect;
export type InsertCuota = typeof cuotas.$inferInsert;
