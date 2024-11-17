DIR=${1:-.}

echo "Processing .java files in $DIR to remove lines containing 'hmsystem'..."


find "$DIR" -type f -name "*.java" | while read -r file; do
  
    if [[ "$OSTYPE" == "darwin"* ]]; then
  
        sed -i '' '/hmsystem/d' "$file"
    else
 
        sed -i '/hmsystem/d' "$file"
    fi
    echo "Processed $file"
done

echo "Done!"