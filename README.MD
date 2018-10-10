Can markdown be used as a convenient and efficient format in 
which to combine test data and business test case comments?

File with data (and metadata?)
example?
- .md file containing a message along with its headers and a description.
The headers might be in in yaml format.
The data might be in json.  Two separate code blocks.
The description could be in plain text under a header.

How would this be useful?
A java process could load the file, parse the markdown, and build an object with that shape.
- thing
-- description
-- headers
-- body

Theoretically, this could be a generic parsing process, ala Jackson ObjectMapper.
For example, heading names could be converted to getter/setter methods on the object.
The text under regular headings could be converted to the values for the fields.
The text under code blocks would be added as a string to that particular field.
Or in the case of something like JSON or YAML could even be further interpreted as a map.

So at this point, I would say experimentation is on to ask:

## What could we do with this kind of rich file data and code generation?

What about pointing to other files?
example?

What about pointing to code invocation?
example?

In general, putting data into the code seems to always
work better than putting code into the data.
We have seen this with ReactJS vs Angular (according to others).

But what the use of markdown offers is the ability to have a hierarchical
structure to the file so that we could have properties and values
where the values can be strictly formatted text this is yet easy to read.