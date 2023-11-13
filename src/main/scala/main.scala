
import cats.effect.{Async, ExitCode, IO, Resource}
import laika.api.*
import laika.format.*
import laika.io.api.TreeTransformer
import laika.io.implicits.*
import laika.io.model.RenderedTreeRoot
import laika.markdown.github.GitHubFlavor
import laika.parse.code.SyntaxHighlighting
import cats.effect.unsafe.implicits.global
import laika.ast.Path.Root
import laika.helium.Helium
import laika.helium.config.{HeliumIcon, IconLink}


@main
def main(): Unit =

  val theme =
    Helium.defaults
      // .site.resetDefaults(true, true, true)
      .site.topNavigationBar(
      homeLink = IconLink.internal(Root /  "/docs/README.md", HeliumIcon.home),
    )
      //.site.tableOfContent(title = "Contents", depth = 3)
      .site.mainNavigation(depth = 3, includePageSections = true)
      .build


  val transformer = Transformer
    .from(Markdown)
    .to(HTML)
    .using(GitHubFlavor, SyntaxHighlighting)
    .parallel[IO]
    .withTheme(theme)

    .build

  val result: IO[RenderedTreeRoot[IO]] = transformer.use {
    _.fromDirectory("src")
      .toDirectory("target")
      .transform
  }


  result.unsafeRunAsync {
    case Left(throwable) => println(throwable)
    case Right(result) => println(" Success! ")
  }

//  val result = transformer.transform("hello *there*")
//  result match
//    case Right(s) => println(s)
//    case Left(v) => println(v)




