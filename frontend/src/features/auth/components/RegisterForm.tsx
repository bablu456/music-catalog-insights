"use client";

import { useState } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { registerSchema, RegisterRequest } from "@/services/auth.service";
import { useAuth } from "@/hooks/useAuth";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { toast } from "sonner";
import { Loader2 } from "lucide-react";

export function RegisterForm() {
  const { register } = useAuth();
  const [isPending, setIsPending] = useState(false);

  const form = useForm<RegisterRequest>({
    resolver: zodResolver(registerSchema),
    defaultValues: {
      name: "",
      email: "",
      password: "",
    },
  });

  const onSubmit = async (data: RegisterRequest) => {
    setIsPending(true);
    try {
      await register(data);
      toast.success("Account created successfully!");
    } catch (error: unknown) {
      const e = error as { response?: { data?: { details?: string[]; message?: string } } };
      const apiError = e.response?.data;
      const errorMessage = apiError?.details?.[0] || apiError?.message || "Registration failed. Please try again.";
      toast.error(errorMessage);
    } finally {
      setIsPending(false);
    }
  };

  return (
    <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
      <div className="space-y-2">
        <Label htmlFor="name">Full Name</Label>
        <Input 
          id="name" 
          placeholder="John Doe" 
          {...form.register("name")} 
        />
        {form.formState.errors.name && (
          <p className="text-sm font-medium text-destructive">
            {form.formState.errors.name.message}
          </p>
        )}
      </div>

      <div className="space-y-2">
        <Label htmlFor="email">Email</Label>
        <Input 
          id="email" 
          type="email" 
          placeholder="you@example.com" 
          {...form.register("email")} 
        />
        {form.formState.errors.email && (
          <p className="text-sm font-medium text-destructive">
            {form.formState.errors.email.message}
          </p>
        )}
      </div>

      <div className="space-y-2">
        <Label htmlFor="password">Password</Label>
        <Input 
          id="password" 
          type="password" 
          placeholder="••••••••" 
          {...form.register("password")} 
        />
        {form.formState.errors.password && (
          <p className="text-sm font-medium text-destructive">
            {form.formState.errors.password.message}
          </p>
        )}
      </div>

      <Button type="submit" className="w-full" disabled={isPending}>
        {isPending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
        Sign up
      </Button>
    </form>
  );
}
