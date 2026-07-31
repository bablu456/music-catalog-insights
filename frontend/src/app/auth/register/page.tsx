import { RegisterForm } from "@/features/auth/components/RegisterForm";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import Link from "next/link";

export const metadata = {
  title: "Register | Music Catalog AI",
};

export default function RegisterPage() {
  return (
    <Card className="border-none shadow-none sm:border-solid sm:shadow-sm bg-transparent sm:bg-card">
      <CardHeader className="space-y-1 text-center sm:text-left">
        <CardTitle className="text-2xl font-bold tracking-tight">Create an account</CardTitle>
        <CardDescription>
          Enter your details below to create your account
        </CardDescription>
      </CardHeader>
      <CardContent className="space-y-4">
        <RegisterForm />
        <div className="text-center text-sm text-muted-foreground mt-4">
          Already have an account?{" "}
          <Link href="/auth/login" className="font-medium text-primary hover:underline transition-colors">
            Log in
          </Link>
        </div>
      </CardContent>
    </Card>
  );
}
