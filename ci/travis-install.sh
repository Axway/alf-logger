#!/bin/sh
set -e

echo "Configuring maven setting.xml"
mkdir -p ~/.m2
cp ci/settings.xml ~/.m2/settings.xml

echo "Configuring keys"
echo "$GPG_SECRET_KEYS" | base64 --decode | gpg --import
echo "$GPG_OWNERTRUST" | base64 --decode | gpg --import-ownertrust
