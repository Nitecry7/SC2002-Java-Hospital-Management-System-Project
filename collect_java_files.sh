SOURCE_DIR=${1:-.}

# Target directory to collect .java files (defaults to "collected_java_files" in current directory)
TARGET_DIR=${2:-./collected_java_files}

# Create the target directory if it doesn't exist
mkdir -p "$TARGET_DIR"

echo "Collecting .java files from $SOURCE_DIR to $TARGET_DIR..."

# Find and copy .java files
find "$SOURCE_DIR" -type f -name "*.java" -exec cp -- "{}" "$TARGET_DIR" \;

echo "Done! All .java files are collected in $TARGET_DIR."