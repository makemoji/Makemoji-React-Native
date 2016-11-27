#!/bin/sh
set -e

mkdir -p "${TARGET_BUILD_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}"

RESOURCES_TO_COPY=${PODS_ROOT}/resources-to-copy-${TARGETNAME}.txt
> "$RESOURCES_TO_COPY"

XCASSET_FILES=()

case "${TARGETED_DEVICE_FAMILY}" in
  1,2)
    TARGET_DEVICE_ARGS="--target-device ipad --target-device iphone"
    ;;
  1)
    TARGET_DEVICE_ARGS="--target-device iphone"
    ;;
  2)
    TARGET_DEVICE_ARGS="--target-device ipad"
    ;;
  3)
    TARGET_DEVICE_ARGS="--target-device tv"
    ;;
  *)
    TARGET_DEVICE_ARGS="--target-device mac"
    ;;
esac

install_resource()
{
  if [[ "$1" = /* ]] ; then
    RESOURCE_PATH="$1"
  else
    RESOURCE_PATH="${PODS_ROOT}/$1"
  fi
  if [[ ! -e "$RESOURCE_PATH" ]] ; then
    cat << EOM
error: Resource "$RESOURCE_PATH" not found. Run 'pod install' to update the copy resources script.
EOM
    exit 1
  fi
  case $RESOURCE_PATH in
    *.storyboard)
      echo "ibtool --reference-external-strings-file --errors --warnings --notices --minimum-deployment-target ${!DEPLOYMENT_TARGET_SETTING_NAME} --output-format human-readable-text --compile ${TARGET_BUILD_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}/`basename \"$RESOURCE_PATH\" .storyboard`.storyboardc $RESOURCE_PATH --sdk ${SDKROOT} ${TARGET_DEVICE_ARGS}"
      ibtool --reference-external-strings-file --errors --warnings --notices --minimum-deployment-target ${!DEPLOYMENT_TARGET_SETTING_NAME} --output-format human-readable-text --compile "${TARGET_BUILD_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}/`basename \"$RESOURCE_PATH\" .storyboard`.storyboardc" "$RESOURCE_PATH" --sdk "${SDKROOT}" ${TARGET_DEVICE_ARGS}
      ;;
    *.xib)
      echo "ibtool --reference-external-strings-file --errors --warnings --notices --minimum-deployment-target ${!DEPLOYMENT_TARGET_SETTING_NAME} --output-format human-readable-text --compile ${TARGET_BUILD_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}/`basename \"$RESOURCE_PATH\" .xib`.nib $RESOURCE_PATH --sdk ${SDKROOT} ${TARGET_DEVICE_ARGS}"
      ibtool --reference-external-strings-file --errors --warnings --notices --minimum-deployment-target ${!DEPLOYMENT_TARGET_SETTING_NAME} --output-format human-readable-text --compile "${TARGET_BUILD_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}/`basename \"$RESOURCE_PATH\" .xib`.nib" "$RESOURCE_PATH" --sdk "${SDKROOT}" ${TARGET_DEVICE_ARGS}
      ;;
    *.framework)
      echo "mkdir -p ${TARGET_BUILD_DIR}/${FRAMEWORKS_FOLDER_PATH}"
      mkdir -p "${TARGET_BUILD_DIR}/${FRAMEWORKS_FOLDER_PATH}"
      echo "rsync -av $RESOURCE_PATH ${TARGET_BUILD_DIR}/${FRAMEWORKS_FOLDER_PATH}"
      rsync -av "$RESOURCE_PATH" "${TARGET_BUILD_DIR}/${FRAMEWORKS_FOLDER_PATH}"
      ;;
    *.xcdatamodel)
      echo "xcrun momc \"$RESOURCE_PATH\" \"${TARGET_BUILD_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}/`basename "$RESOURCE_PATH"`.mom\""
      xcrun momc "$RESOURCE_PATH" "${TARGET_BUILD_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}/`basename "$RESOURCE_PATH" .xcdatamodel`.mom"
      ;;
    *.xcdatamodeld)
      echo "xcrun momc \"$RESOURCE_PATH\" \"${TARGET_BUILD_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}/`basename "$RESOURCE_PATH" .xcdatamodeld`.momd\""
      xcrun momc "$RESOURCE_PATH" "${TARGET_BUILD_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}/`basename "$RESOURCE_PATH" .xcdatamodeld`.momd"
      ;;
    *.xcmappingmodel)
      echo "xcrun mapc \"$RESOURCE_PATH\" \"${TARGET_BUILD_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}/`basename "$RESOURCE_PATH" .xcmappingmodel`.cdm\""
      xcrun mapc "$RESOURCE_PATH" "${TARGET_BUILD_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}/`basename "$RESOURCE_PATH" .xcmappingmodel`.cdm"
      ;;
    *.xcassets)
      ABSOLUTE_XCASSET_FILE="$RESOURCE_PATH"
      XCASSET_FILES+=("$ABSOLUTE_XCASSET_FILE")
      ;;
    *)
      echo "$RESOURCE_PATH"
      echo "$RESOURCE_PATH" >> "$RESOURCES_TO_COPY"
      ;;
  esac
}
if [[ "$CONFIGURATION" == "Debug" ]]; then
  install_resource "Makemoji-SDK/Pod/Assets/MEBackIcon.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEBackIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MECameraIcon.png"
  install_resource "Makemoji-SDK/Pod/Assets/MECameraIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEChatBotLeft@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEChatBotRight@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEDeleteIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEFlashtagIcon.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEFlashtagIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEGridIcon.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEGridIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEKeyPop@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEMessageEntryBackground.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEMessageEntryBackground@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEMessageEntryInputField.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEMessageEntryInputField@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-animals@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-clothing@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-dating@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-expression@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-food@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-gaming@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-gif@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-hands@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-music@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-myemoji@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-newsfeed@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-objects@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-osemoji@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-politics@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-popculture@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-sports@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-trending@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-used@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEPlaceholder.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEPlayOverlay@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEReactionAdd@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MERecent.png"
  install_resource "Makemoji-SDK/Pod/Assets/MERecent@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MESearchIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MESmallClose@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEStarIcon.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEStarIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/METrendingIcon.png"
  install_resource "Makemoji-SDK/Pod/Assets/METrendingIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/DTLoupe.bundle"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEDeleteBackwardsButton@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEDeleteBackwardsButtonLarge@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEGlobeButton@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-animals@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-clothing@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-dating@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-emoji@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-expression@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-food@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-gaming@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-hands@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-keyboard@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-music@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-newsfeed@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-objects@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-osemoji@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-politics@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-popculture@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-sports@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-trending@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-used@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboardPlayOverlay@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEShiftButton@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEShiftButtonEnabled@2x.png"
fi
if [[ "$CONFIGURATION" == "Release" ]]; then
  install_resource "Makemoji-SDK/Pod/Assets/MEBackIcon.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEBackIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MECameraIcon.png"
  install_resource "Makemoji-SDK/Pod/Assets/MECameraIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEChatBotLeft@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEChatBotRight@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEDeleteIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEFlashtagIcon.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEFlashtagIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEGridIcon.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEGridIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEKeyPop@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEMessageEntryBackground.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEMessageEntryBackground@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEMessageEntryInputField.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEMessageEntryInputField@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-animals@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-clothing@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-dating@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-expression@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-food@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-gaming@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-gif@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-hands@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-music@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-myemoji@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-newsfeed@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-objects@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-osemoji@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-politics@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-popculture@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-sports@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-trending@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MENav-used@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEPlaceholder.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEPlayOverlay@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEReactionAdd@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MERecent.png"
  install_resource "Makemoji-SDK/Pod/Assets/MERecent@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MESearchIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MESmallClose@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEStarIcon.png"
  install_resource "Makemoji-SDK/Pod/Assets/MEStarIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/METrendingIcon.png"
  install_resource "Makemoji-SDK/Pod/Assets/METrendingIcon@2x.png"
  install_resource "Makemoji-SDK/Pod/Assets/DTLoupe.bundle"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEDeleteBackwardsButton@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEDeleteBackwardsButtonLarge@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEGlobeButton@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-animals@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-clothing@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-dating@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-emoji@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-expression@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-food@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-gaming@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-hands@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-keyboard@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-music@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-newsfeed@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-objects@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-osemoji@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-politics@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-popculture@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-sports@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-trending@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboard-used@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEKeyboardPlayOverlay@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEShiftButton@2x.png"
  install_resource "MakemojiSDK-KeyboardExtension/Pod/Assets/MEShiftButtonEnabled@2x.png"
fi

mkdir -p "${TARGET_BUILD_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}"
rsync -avr --copy-links --no-relative --exclude '*/.svn/*' --files-from="$RESOURCES_TO_COPY" / "${TARGET_BUILD_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}"
if [[ "${ACTION}" == "install" ]] && [[ "${SKIP_INSTALL}" == "NO" ]]; then
  mkdir -p "${INSTALL_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}"
  rsync -avr --copy-links --no-relative --exclude '*/.svn/*' --files-from="$RESOURCES_TO_COPY" / "${INSTALL_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}"
fi
rm -f "$RESOURCES_TO_COPY"

if [[ -n "${WRAPPER_EXTENSION}" ]] && [ "`xcrun --find actool`" ] && [ -n "$XCASSET_FILES" ]
then
  # Find all other xcassets (this unfortunately includes those of path pods and other targets).
  OTHER_XCASSETS=$(find "$PWD" -iname "*.xcassets" -type d)
  while read line; do
    if [[ $line != "${PODS_ROOT}*" ]]; then
      XCASSET_FILES+=("$line")
    fi
  done <<<"$OTHER_XCASSETS"

  printf "%s\0" "${XCASSET_FILES[@]}" | xargs -0 xcrun actool --output-format human-readable-text --notices --warnings --platform "${PLATFORM_NAME}" --minimum-deployment-target "${!DEPLOYMENT_TARGET_SETTING_NAME}" ${TARGET_DEVICE_ARGS} --compress-pngs --compile "${BUILT_PRODUCTS_DIR}/${UNLOCALIZED_RESOURCES_FOLDER_PATH}"
fi
