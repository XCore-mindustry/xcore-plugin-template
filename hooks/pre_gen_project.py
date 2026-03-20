import re
import sys


def require(pattern: str, value: str, message: str) -> None:
    if not re.fullmatch(pattern, value):
        print(message, file=sys.stderr)
        raise SystemExit(1)


artifact_id = "{{ cookiecutter.artifact_id }}"
group_id = "{{ cookiecutter.group_id }}"
package_name = "{{ cookiecutter.package_name }}"
main_class_name = "{{ cookiecutter.main_class_name }}"

require(r"[a-z0-9]+(?:-[a-z0-9]+)*", artifact_id,
        "artifact_id must contain lowercase letters, digits, and hyphens only")
require(r"[a-z][a-z0-9_]*(?:\.[a-z][a-z0-9_]*)*", group_id,
        "group_id must be a valid lowercase Java-style package")
require(r"[a-z][a-z0-9_]*(?:\.[a-z][a-z0-9_]*)*", package_name,
        "package_name must be a valid lowercase Java package")
require(r"[A-Z][A-Za-z0-9_]*", main_class_name,
        "main_class_name must be a valid Java class name starting with an uppercase letter")
