from pathlib import Path
import stat
import shutil


project_dir = Path.cwd()
gradlew = project_dir / "gradlew"

if gradlew.exists():
    current_mode = gradlew.stat().st_mode
    gradlew.chmod(current_mode | stat.S_IXUSR | stat.S_IXGRP | stat.S_IXOTH)

if "{{ cookiecutter.use_flubundle }}" != "True":
    bundles_dir = project_dir / "src" / "main" / "resources" / "bundles"
    if bundles_dir.exists():
        shutil.rmtree(bundles_dir)

print("\nProject created successfully.")
print(f"- Directory: {project_dir}")
print("- Next steps:")
print("  1. cd {}".format(project_dir.name))
print("  2. ./gradlew shadowJar")
