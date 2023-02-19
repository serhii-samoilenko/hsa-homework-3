#!/bin/bash
# Base url
base_url="http://example.com/path/to/endpoint"

# Set the number of coordinates to generate
num_coords=1000

# Set the range of latitude and longitude values to generate
min_lat=-90
max_lat=90
min_lon=-180
max_lon=180

# Generate the coordinates and write them to a file
for ((i=1; i<=num_coords; i++)); do
  lat=$(awk -v min=$min_lat -v max=$max_lat 'BEGIN{srand(); print min+rand()*(max-min)}')
  lon=$(awk -v min=$min_lon -v max=$max_lon 'BEGIN{srand(); print min+rand()*(max-min)}')
  echo "$lat,$lon" >> coordinates.txt
done

