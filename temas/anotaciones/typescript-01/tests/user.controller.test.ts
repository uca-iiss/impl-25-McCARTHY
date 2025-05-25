import 'reflect-metadata';
import { simulateRequest } from '../src/simulate';
import { UserController } from '../src/user.controller';

describe('UserController Decorator Behavior', () => {
    it('should greet a user correctly', () => {
        const consoleSpy = jest.spyOn(console, 'log').mockImplementation(() => {});
        simulateRequest(UserController, "GET", "/api/user/greet", "Alice");
        expect(consoleSpy).toHaveBeenCalledWith(expect.stringContaining("Hello, Alice!"));
        consoleSpy.mockRestore();
    });

    it('should allow admin to perform action', () => {
        const consoleSpy = jest.spyOn(console, 'log').mockImplementation(() => {});
        simulateRequest(UserController, "POST", "/api/user/admin", { name: "Bob", role: "admin" });
        expect(consoleSpy).toHaveBeenCalledWith(expect.stringContaining("Admin Bob performed an action."));
        consoleSpy.mockRestore();
    });

    it('should block non-admin from performing action', () => {
        const consoleSpy = jest.spyOn(console, 'log').mockImplementation(() => {});
        simulateRequest(UserController, "POST", "/api/user/admin", { name: "Eve", role: "user" });
        expect(consoleSpy).toHaveBeenCalledWith(expect.stringContaining("❌ Error: Unauthorized"));
        consoleSpy.mockRestore();
    });

    it('should throw error if non-string is passed to greet', () => {
        const consoleSpy = jest.spyOn(console, 'log').mockImplementation(() => {});
        simulateRequest(UserController, "GET", "/api/user/greet", 123);
        expect(consoleSpy).toHaveBeenCalledWith(expect.stringContaining("❌ Error: Invalid input"));
        consoleSpy.mockRestore();
    });
});
