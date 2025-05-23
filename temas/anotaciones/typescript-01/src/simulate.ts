import "reflect-metadata";
import { HttpMethod } from "./decorators";

export function simulateRequest(
    controllerClass: any,
    method: HttpMethod,
    path: string,
    ...args: any[]
) {
    const basePath = Reflect.getMetadata("basePath", controllerClass);
    const routes = Reflect.getMetadata("routes", controllerClass) || [];

    const route = routes.find(
        (r: any) => r.method === method && `${basePath}${r.path}` === path
    );

    if (!route) {
        console.log(`❌ 404 - No route for ${method} ${path}`);
        return;
    }

    const instance = new controllerClass();
    try {
        const result = (instance as any)[route.handler](...args);
        console.log(`✅ Resultado: ${result}`);
    } catch (e: any) {
        console.log(`❌ Error: ${e.message}`);
    }
}
