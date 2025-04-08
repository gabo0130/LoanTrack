"use client";

import { useEffect, useState } from "react";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { CheckCircle, AlertTriangle, XCircle } from "lucide-react";
import { cn } from "lib/utils";

type AlertType = "success" | "error" | "warning";

interface AlertMessageProps {
  message: string;
  type?: AlertType;
  duration?: number; // opcional: tiempo que se muestra (ms)
}

const AlertMessage: React.FC<AlertMessageProps> = ({
  message,
  type = "success",
  duration = 3000,
}) => {
  const [visible, setVisible] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => setVisible(false), duration);
    return () => clearTimeout(timer);
  }, [duration]);

  if (!visible) return null;

  const icon = {
    success: <CheckCircle className="h-5 w-5 text-green-600" />,
    error: <XCircle className="h-5 w-5 text-red-600" />,
    warning: <AlertTriangle className="h-5 w-5 text-yellow-600" />,
  };

  const color = {
    success: "border-green-500 bg-green-50",
    error: "border-red-500 bg-red-50",
    warning: "border-yellow-500 bg-yellow-50",
  };

  return (
    <Alert className={cn("flex items-center", color[type])}>
      {icon[type]}
      <div className="ml-3">
        <AlertTitle className="capitalize">{type}</AlertTitle>
        <AlertDescription>{message}</AlertDescription>
      </div>
    </Alert>
  );
};

export default AlertMessage;
