
  # IntentUtils

  ![AndroidStudio 3.1.4](https://img.shields.io/badge/Android_Studio-3.1.4-brightgreen.svg)
  ![minSDK 16](https://img.shields.io/badge/minSDK-API_16-orange.svg?style=flat)
  ![targetSDK 27](https://img.shields.io/badge/targetSDK-API_27-blue.svg)

  `IntentUtils` is a small wrapper for handling intent opening via `startActitvy`.

  It handles incoming intents the following way:
  1. If there is only one App that can handle the Intent it is open directly
  2. If there are multiple apps for hanlding the intent, the chooser is shown
  3. If there is no app that could handle the intent, a Dialog is open and shows a hint


  ## Installation

Add `jitpack` to your repositories in Project `build.gradle` :
```gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
    ...
}
```

Add dependency to your app `build.gradle` :
```gradle
implementation 'com.github.grumpyshoe:android-module-intentutils:1.1.0'
```


## Usage

This library contains a [extension function](https://kotlinlang.org/docs/reference/extensions.html) for handling intent opening :

### Start activity


```kotlin
/* kotlin */

// example intent for open a email app
val intent = Intent(Intent.ACTION_SEND)
intent.type = "message/rfc822"
intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com"))
intent.putExtra(Intent.EXTRA_SUBJECT, "subject of email")
intent.putExtra(Intent.EXTRA_TEXT, "body of email")

intent.open(this)
```

```java
/* java */

// example intent for open a email app
Intent intent = new Intent(Intent.ACTION_SEND);
intent.setType("message/rfc822");
intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
intent.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
intent.putExtra(Intent.EXTRA_TEXT, "body of email");

IntentUtilsKt.open(intent, this, null);
```

### Start activity for result

```kotlin
/* kotlin */

// example intent for open a gallery app
val intent = Intent()
intent.type = "image/*"
intent.action = Intent.ACTION_PICK

intent.openForResult(this, 3155)
```

```java
/* java */

// example intent for open a gallery app
Intent intent = new Intent();
intent.setType("image/*");
intent.setAction(Intent.ACTION_PICK);

IntentUtilsKt.openForResult(intent, this, 3155, null);
```

## Customization

### Multiple Apps available
If there are multiple app that are able to handle the intent request, the chooser will be shown.
For customize the title of that chooser override the string value `app_chooser_dialog_title` with the corresponding text. The default for this value is: "*Choose an app*".

### No App available
If no apps can be found that could handle this intent a ÀlertDialog is shown.
This dialog contains a **title** (default value: "*Hint*"),a **message** (default value: "*There are no apps that could handle this content.*") and a **button** (default label "*OK*").

To set a custom _title_ and _message_ there are two ways:

1. Override the string value `no_app_for_intent_dialog_title`, `no_app_for_intent_dialog_message` and `no_app_for_intent_dialog_btn_ok_text` with the corresponding text.

2. You can add a instance of `NoAppAvailable` and define your customized texts there (for all or just for some fields):

  ```kotlin
  /* kotlin */
  ...

  // all fields
  i.open(this, NoAppAvailable("Custom Title Text", "Custom Message Text", "Custom Button Text"))

  // or just the fields you wnat to change
  i.open(this, NoAppAvailable(message ="Custom Message Text"))

  ```

  ```java
  /* java */
  ...

  // all fields
  IntentUtilsKt.open(i, this, new NoAppAvailable("Custom Title Text", "Custom Message Text", "Custom Button Text"))

  // or just the fields you wnat to change
  IntentUtilsKt.open(i, this, new NoAppAvailable(null, "Custom Message Text", null));
  ```



## Need Help or something missing?

Please [submit an issue](https://github.com/grumpyshoe/android-module-intentutils/issues) on GitHub.


## License

This project is licensed under the terms of the MIT license. See the [LICENSE](LICENSE) file.

## Build Environment
```
Android Studio 3.1.4
Build #AI-173.4907809, built on July 23, 2018
JRE: 1.8.0_152-release-1024-b01 x86_64
JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
Mac OS X 10.13.4
```
