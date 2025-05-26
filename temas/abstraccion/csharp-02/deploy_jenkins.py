import subprocess
import sys

def run_command(description, command):
    print(f"\n{description}...")
    try:
        subprocess.run(command, check=True)
        print("Éxito")
    except subprocess.CalledProcessError as e:
        print(f"Error al ejecutar: {' '.join(command)}")
        sys.exit(e.returncode)

def main():
    run_command("Inicializando Terraform", ["terraform", "init"])
    run_command("Validando configuración", ["terraform", "validate"])
    run_command("Aplicando infraestructura", ["terraform", "apply", "-auto-approve"])

if __name__ == "__main__":
    main()