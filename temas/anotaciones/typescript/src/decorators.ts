import "reflect-metadata";

export type HttpMethod = "GET" | "POST";

export function Controller(basePath: string): ClassDecorator {
    return (target) => {
        Reflect.defineMetadata("basePath", basePath, target);
    };
}

export function Route(method: HttpMethod, path: string): MethodDecorator {
    return (target, propertyKey) => {
        const existingRoutes = Reflect.getMetadata("routes", target.constructor) || [];
        existingRoutes.push({ method, path, handler: propertyKey });
        Reflect.defineMetadata("routes", existingRoutes, target.constructor);
    };
}

export function Log(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
    const original = descriptor.value;
    descriptor.value = function (...args: any[]) {
        console.log(`[LOG] Llamando a ${propertyKey.toString()} con ${JSON.stringify(args)}`);
        return original.apply(this, args);
    };
}

export function Auth(requiredRole: string): MethodDecorator {
    return (target, propertyKey, descriptor: PropertyDescriptor) => {
        const original = descriptor.value;
        descriptor.value = function (user: any, ...args: any[]) {
            if (!user || user.role !== requiredRole) {
                throw new Error("Unauthorized");
            }
            return original.call(this, user, ...args);
        };
    };
}

export function ValidateStringParams(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
    const original = descriptor.value;
    descriptor.value = function (...args: any[]) {
        if (args.some(arg => typeof arg !== "string")) {
            throw new Error("Invalid input: expected string parameters");
        }
        return original.apply(this, args);
    };
}
