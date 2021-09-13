# URL protocol handler for "data:" inline data for java

Java Swing application can show HTML contents on JEditorPane, JTextPane and JTextArea widget.
These component only support "http:", "https:" and "file:" protocols, but there is no support for
"data:" protocol.

The URL protocol handler provide "data:" handler.
A "data:" scheme is used for inline image, like `<img src="data:image/png;base64,abc123"/>` form.

The handler allows you to use inline image on your application.

## Usage

Just call `URLProtocolHandler.install()` method before start GUI that use data URL protocol.

```java
import tokyo.northside.protocol.URLProtocolHandler;

URLProtocolHandler.install();
```


## Copyright and License

URL protocol handler for "data:" inline data

Copyright (C) 2021 Hiroshi Miura

Copyright (C) 2012 Joop Eggen

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>