# URL protocol handler for "data:" inline data for java

Java Swing application can show HTML contents on JEditorPane, JTextPane and JTextArea widget.
These component only support "http:", "https:" and "file:" protocols, but there is no support for
"data:" protocol.

The URL protocol handler provide "data:" handler.
A "data:" scheme is used for inline image, like `<img src="data:image/png;base64,abc123"/>` form.

The handler allows you to use inline image on your application.

## Specification

You can find an Data URIs specification on [RFC2397](https://datatracker.ietf.org/doc/html/rfc2397) and detailed explanation on [MDN Web Docs moz://a](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/Data_URIs)

## Usage

Just call `URLProtocolHandler.install()` method before start GUI that use data URL protocol.

```java
import tokyo.northside.protocol.URLProtocolHandler;

URLProtocolHandler.install();
```

## What doing under the hood?

I've inspired it from [stackoverflow answer by Joop Eggen](https://stackoverflow.com/questions/9388264/jeditorpane-with-inline-image/9388757#9388757).
The handler basically does a samething with the article, but does it better and generalize, and registered in Maven central.


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
