import { Controller, Route, Log, Auth, ValidateStringParams } from "./decorators";

@Controller("/api/user")
export class UserController {
    @Route("GET", "/greet")
    @Log
    @ValidateStringParams
    greet(name: string): string {
        return `Hello, ${name}!`;
    }

    @Route("POST", "/admin")
    @Auth("admin")
    @Log
    performAdminAction(user: any): string {
        return `Admin ${user.name} performed an action.`;
    }
}
