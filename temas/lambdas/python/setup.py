from setuptools import setup, find_packages

setup(
    name='lambdas_logs',
    version='0.1',
    packages=find_packages(include=['app', 'app.*']),
    entry_points={
        'console_scripts': [
            'procesador=main:main',
        ],
    },
    include_package_data=True,
    description='Procesamiento de logs con funciones lambda'
)

