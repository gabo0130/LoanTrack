"use client";

import React, { createContext, useContext, useState, ReactNode } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { CheckCircle, AlertTriangle, XCircle, X } from "lucide-react";
import { cn } from "lib/utils";

type AlertType = "success" | "error" | "warning";

interface AlertContextProps {
  showAlert: (message: string, type?: AlertType, duration?: number) => void;
}

const AlertContext = createContext<AlertContextProps | undefined>(undefined);

export const useAlert = () => {
  const context = useContext(AlertContext);
  if (!context) {
    throw new Error("useAlert must be used within an AlertProvider");
  }
  return context;
};

export const AlertProvider = ({ children }: { children: ReactNode }) => {
  const [message, setMessage] = useState<string>("");
  const [type, setType] = useState<AlertType>("success");
  const [visible, setVisible] = useState<boolean>(false);

  const showAlert = (msg: string, alertType: AlertType = "success", duration = 3000) => {
    setMessage(msg);
    setType(alertType);
    setVisible(true);

    setTimeout(() => {
      setVisible(false);
    }, duration);
  };

  const icon = {
    success: <CheckCircle className="h-6 w-6 text-green-600" />, 
    error: <XCircle className="h-6 w-6 text-red-600" />, 
    warning: <AlertTriangle className="h-6 w-6 text-yellow-600" />, 
  };

  const color = {
    success: "border-green-500 bg-green-50",
    error: "border-red-500 bg-red-50",
    warning: "border-yellow-500 bg-yellow-50",
  };

  return (
    <AlertContext.Provider value={{ showAlert }}>
      <AnimatePresence>
        {visible && (
          <motion.div
            initial={{ opacity: 0, scale: 0.8 }}
            animate={{ opacity: 1, scale: 1 }}
            exit={{ opacity: 0, scale: 0.8 }}
            transition={{ duration: 0.3 }}
            className="fixed inset-0 flex items-center justify-center backdrop-blur-sm"
            style={{ zIndex: 9999 }}
          >
            <motion.div
              initial={{ y: -20, opacity: 0 }}
              animate={{ y: 0, opacity: 1 }}
              exit={{ y: -20, opacity: 0 }}
              transition={{ duration: 0.3 }}
              className={cn("relative p-5 rounded-lg shadow-lg w-96", color[type])}
            >
              <button onClick={() => setVisible(false)} className="absolute top-2 right-2">
                <X className="h-5 w-5 text-gray-500 hover:text-gray-700" />
              </button>
              <div className="flex items-center gap-3">
                {icon[type]}
                <div>
                  <AlertTitle className="capitalize text-lg font-semibold">{type}</AlertTitle>
                  <AlertDescription>{message}</AlertDescription>
                </div>
              </div>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
      {children}
    </AlertContext.Provider>
  );
};