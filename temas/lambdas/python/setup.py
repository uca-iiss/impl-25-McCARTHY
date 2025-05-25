from setuptools import setup, find_packages

setup(
    name='lambdas_logs',
    version='0.1',
    packages=find_packages(where='.', exclude=["tests*"]),
    install_requires=[],
    entry_points={
        'console_scripts': [
            'procesador=main:main',  
        ]
    }
)

